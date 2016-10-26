package gmit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*Word entry gets dictionary parsed, ignore words parsed and parses a provided book 
 * filtering the ignore words and associating the word definition if such definition exists in dictionary, 
 * else sets definition to Undefined, it also counts the pages where those words are found (40 lines per page). */

public class WordEntry implements Parser {

	/*
	 * Dictionary is a HashMap that will contain a relation of words(keys) and definitions(values).
	 */
	private Map<String, ArrayList<String>> dictionary = new HashMap<String, ArrayList<String>>();

	/*
	 * ignoreWords will contain the ignore words to filter words from the book
	 * parsed
	 */
	private Set<String> ignoreWords = new TreeSet<String>();

	/*
	 * maps will associate words in book parsed with their definitions and pages
	 * and store those values in WordDetail class
	 */
	private Map<String, WordDetail> map = new HashMap<String, WordDetail>();

	/*
	 * parse takes a book, creates a tree containing ignore words, and then
	 * takes book words filtering by ignore words, if the words have been
	 * already added it will just add the page if it doesn't it will take the
	 * word search in dictionary, get the definition (If there is no definition
	 * for that word sets it to Undefined), the page and add it to the map using
	 * the word as key and definition and page as values within WordDetail
	 * class. counts pages an gets returns an instantiation of WordEntry class
	 */
	public WordEntry(Map<String, ArrayList<String>> dictionary){
		this.dictionary = dictionary;
	}
	public WordEntry parse(String fileName) throws Exception {
		// Set the reader
		BufferedReader br = null;
		try {
			// Assign reader a file to read stream from
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			// Get the ignore words into set
			ignoreWords = parseIgnoreWords("stopwords.txt");
			// Set page value to 1
			int page = 1;
			int lineCounter = 0;
			String line = null;
			long startTime = System.nanoTime();
			/* Start reading from book until it gets the end of the file */
			while ((line = br.readLine()) != null) {
				/* For every line read increment line counter */
				lineCounter++;
				/*
				 * Every time line counter get incremented by 40 increment page
				 */
				if (lineCounter % 40 == 0) {
					page++;
				}
				/* Split line by [\\s@&.,;:?$+-]+ characters */
				String[] words = line.split("[\\s@&.,;:?$+-]+");
				/*
				 * For every word, reformat them if contain symbols, ignore
				 * ignore words and blank spaces, if the word is in the map
				 * already add page, else add word and get page and definition
				 * from dictionary and add them to WordDetail, add WordDetail to
				 * the word
				 */
				for (int i = 0; i < words.length; i++) {
					words[i] = words[i].toUpperCase();
					words[i] = words[i].replaceAll("[\\s@&.,;:?!$+-/*()_\"]+", "");
					words[i] = words[i].replace("[", "");
					words[i] = words[i].replace("]", "");
					if (ignoreWords.contains(words[i].toLowerCase()))/* O (log n)*/
						continue;
					if (words[i].isEmpty())/* O(1) */
						continue;// skip // 5
					if (map.containsKey(words[i])) { /* O(1) */
						map.get(words[i]).addIndex(page);/* O(1) - O(log(n)) */
					} else {
						WordDetail wd = new WordDetail();
						wd.addIndex(page);/* O(log(n)) */
						map.put(words[i], wd);/* O(1) */
						if (dictionary.get(words[i]) != null) { /* O(1) */
							map.get(words[i]).setDefinition(dictionary.get(words[i]));/* O(1) */
						} else {
							map.get(words[i]).setDefinition("Undefined");/* O(1) */
						}
					}
				}
			}
			long time = System.nanoTime() - startTime;
			System.out.println("Book Parsing Elapsed time (ms): " + (time / 1000000));
			//System.out.println(map.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return this;
	}

	public Set<String> parseIgnoreWords(String fileName) throws Exception {
		/* Set to store ignore words */
		Set<String> ignore = new TreeSet<String>();
		BufferedReader br = null;
		String line = null;
		try {
			/* Read ignore words into tree */
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			while ((line = br.readLine()) != null) {
				ignore.add(line); /* O(log(n)) */
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* Return set containing ignore words */
		//System.out.println(ignore.size());
		return ignore;
	}

	/*
	 * method getWordDefinition takes in a word and return its definition if map
	 * contains it else, returns "Definition not Available"
	 */
	public String getWordDefinition(String key) {
		if (map.get(key) != null) {
			return map.get(key).getDefinitions().toString();
		} else {
			return "Definition not Available";
		}
	}

	/*
	 * method getWordPages takes in a word and return pages where word can be
	 * found if map contains it else, returns word is not in the book
	 */
	public String getWordPages(String key) {
		if (map.get(key.toUpperCase()) != null) {
			return map.get(key.toUpperCase()).getIndices().toString();
		} else {
			return key.toUpperCase() + " is not in this book";
		}

	}
}
