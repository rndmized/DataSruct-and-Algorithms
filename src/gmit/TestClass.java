package gmit;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class TestClass {

	private Dictionary dc = new Dictionary();
	private Map<String, ArrayList<String>> dictionary = dc.parse("dictionary.csv");
	
	public static void main(String[] args) {
		// Instantiate TestClass
		TestClass tc = new TestClass();
		// Scanner object to read user input
		Scanner console = new Scanner(System.in);
		// Store user input into choice
		int choice;
		// Print menu
		do {
			System.out.println("Welcome to the Vocabulary Learning Experience Program.");
			System.out.println("Please select the book you want to check a word meaning from.");
			System.out.println("1.PoblachNaHEireann.");
			System.out.println("2.De Bello Gallico.");
			System.out.println("3.War And Peace.");
			choice = console.nextInt();
			switch (choice) {
			case 1:
				tc.getWord("PoblachtNaHEireann.txt");
				break;
			case 2:
				tc.getWord("DeBelloGallico.txt");
				break;
			case 3:
				tc.getWord("WarAndPeace-LeoTolstoy.txt");
				break;
			case -1:
				System.exit(1);
				break;
			default:
				System.out.println("Invalid Option");
			}
			// Exit loop if choice is -1

		} while (choice != -1);
	}

	/*
	 * getWord takes book url and ask user to input a word to search in the book
	 * and prints its definition if found and the pages.
	 */
	public void getWord(String url) {
		Scanner scan = new Scanner(System.in);
		String searchWord;
		try {
			WordEntry we = new WordEntry(dictionary);
			we = we.parse(url);
			do {
				System.out.println("Please enter word to search:");
				searchWord = scan.next();
				System.out.println(we.getWordDefinition(searchWord.toUpperCase()));
				System.out.println(we.getWordPages(searchWord.toUpperCase()));
			} while (!(searchWord.equals("-1")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Book File not Found");
		}
	}
}
