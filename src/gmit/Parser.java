package gmit;

public interface Parser {

	/*
	 * The interface parser implements a parse method that requires a file to
	 * parse from and return an object containing the parsed file
	 */
	public Object parse(String fileName) throws Exception;

}
