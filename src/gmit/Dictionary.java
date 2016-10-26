package gmit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dictionary implements Parser {

	// HashMap -> < word, definition>
	private Map<String, ArrayList<String>> maps = new HashMap<String, ArrayList<String>>();

	// parse() returns a map linking words(key) with definitions(value) from a
	// file
	public Map<String, ArrayList<String>> parse(String csvFile) {

		BufferedReader br = null;
		String line;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			long startTime = System.nanoTime();
			while ((line = br.readLine()) != null) {
				/* Reads the first word contained within quotation marks */
				if (line.charAt(0) == '"') {
					String word = line.substring(1, line.indexOf('"', 1));
					ArrayList<String> definition = new ArrayList<String>();
					/*
					 * While the end of the line is not a quotation mark keep
					 * adding(concatenating) lines into line string
					 */
					do {
						line += br.readLine();
					} while ((line.charAt(line.length() - 1) != '"'));
					/* If word(key) is already in the map add line to value */
					if (maps.containsKey(word.toUpperCase())) {
						maps.get(word.toUpperCase()).add(line + "\n");/* O(1)*/
					} else {
						/* add word and definition into the map */
						definition.add(line + "\n");/* O(1)*/
						maps.put(word.toUpperCase(), definition);/* O(1)*/
					}
				}
			}
			long time = System.nanoTime() - startTime;
			System.out.println("Dictionary Elapsed time (ms): " + (time / 1000000));
			//System.out.println(maps.size());
			/* Handling Exceptions */
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
		/* returns map containing words and definitions */
		return maps;
	}

	/*
	 * returns a definition for a given word, if it is not in the dictionary
	 * return "Definition not Available"
	 */
	public String getWordDefinition(String key) {
		if (maps.get(key) != null) {
			return maps.get(key).toString();/* O(1)*/
		} else {
			return "Definition not Available";
		}
	}
}
