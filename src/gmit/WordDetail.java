package gmit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class WordDetail {
	
	/* TreeSet containing pages */
	private Set<Integer> pages = new TreeSet<Integer>();
	
	/* ArrayList of Strings containing definitions */
	private List<String> definitions = new ArrayList<String>();
	
	/* Empty constructor */
	public WordDetail(){}

	/* Method adding a page to the tree set*/
	public void addIndex(int page){		
		pages.add(page);/* O(log(n)) */
	}
	
	/* Method returning a set of integers containing pages */
	public Set<Integer> getIndices()	{
		return pages;
	}
	
	/* method returning an ArrayList of strings containing definitions*/
	public List<String> getDefinitions() {
		return definitions;
	}
	
	/* method to set definition to a given array list of strings*/
	public void setDefinition(ArrayList<String> definition) {
		this.definitions = definition;
	}
	
	/* mathod adding a string to the array list of definitions */
	public void setDefinition(String definition) {
		this.definitions.add(definition);/* o(1)*/
	}
}
