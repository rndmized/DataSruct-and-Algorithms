******************************************************************************************
	Data Structures & Algorythms Project 2016 by Albert Rando (G00330058)
******************************************************************************************

This API is composed by 4 classes and an interface.
- The interface Parser implements the function parse and returns an oject (a collection) and requires an argument string (file location).
- Dictionary contains the parse method from Parser, takes in a dictionary 
(dictionary.csv) and returns a HashMap containing words(strings as keys) and its definitions (as values stored in an ArrayList). It also contains the method getWordDefinition that takes a string key and returns its definition if it is in the map or else an error message.
- WordDetail has a TreeSet to store Integer values and an ArrayList to Store Strings for definitions plus getters and setters.
- WordEntry implements parse takes file location, a HashMap containing a dictionary, and a list of ignore words. Its parses the ignore words into a tree set, and starts reading the book, breaks it into words and adds new words into a HashMap containing words(key) and WordDetais(value), gets its definition from the dictionary class and adds the page number to WordDetail of the word.
-TestClass main method contains a menu to interact and switch books, and search words from the HashMap that contains the book getting back its definition and the pages where the word is.