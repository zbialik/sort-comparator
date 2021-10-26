package prefix_to_postfix;

import lists.*;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

/**
 * This class executes the main method which takes in 2 arguments: inputFile and
 * outputFile The program will functionally do the following: - reads file line
 * by line (assumes each line is prefix expression) - converts line to postfix
 * expression using recursive solution - outputs conversion to output file
 * 
 * @author zachbialik
 * 
 *         inputs: 1. inputFile (string) 2. outputFile (string)
 *
 */
public class PrefixToPostfixProcessor {

	private static Logger LOGGER = LogManager.getLogger(PrefixToPostfixProcessor.class);

	private static FileReader inputReader = null;
	private static BufferedWriter outputWriter = null;

	private static boolean prefixValid = true;
	private static String errorMessage = "prefix is invalid";

	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			String msg = "you must provide exactly 2 input args: inputFile and outputFile.";
			throw new Exception(msg);
		}

		String inputFile = args[0];
		String outputFile = args[1];

		LOGGER.info("reading user inputs");
		LOGGER.debug("input file: " + inputFile);
		LOGGER.debug("output file: " + outputFile);

		try {
			inputReader = new FileReader(inputFile); // input file
			outputWriter = new BufferedWriter(new FileWriter(outputFile)); // output file

			outputWriter.write("");
			processFile(inputReader, outputWriter); // execute main process

		} catch (FileNotFoundException e) {
			LOGGER.error("file not found:" + inputFile);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("i/o error thrown: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (inputReader != null) {
					inputReader.close();
				}
				if (outputWriter != null) {
					outputWriter.close();
				}
			} catch (IOException e) {
				LOGGER.error("error closing stream.");
			}
		}
	}

	/**
	 * Reads in the input file line by line and converts each prefix expression to
	 * postfix and writes to the output file
	 * 
	 * @param inputReader
	 * @param outputWriter
	 * @throws IOException
	 * @throws Exception
	 */
	private static void processFile(FileReader inputReader, BufferedWriter outputWriter) throws IOException, Exception {

		String prefix = "";
		String postfix = "";

		String line;
		ListLinked prefixList = new ListLinked();
		int closedIndex;

		LOGGER.info("Begin reading input file line by line.");

		try (BufferedReader br = new BufferedReader(inputReader)) {

			while ((line = br.readLine()) != null) { // execute conversion for each line

				if (!line.isBlank() && !line.isEmpty()) { // only process lines that contain non-whitespace
					prefix = line.trim().replaceAll("\\s+",""); // remove white space
					prefixList.clear();
					
					if (!isOperator(prefix.charAt(0))) {
						prefixValid = false;
						errorMessage = "first character in prefix expression must be operator (char: "+prefix.charAt(0)+")";
					} else {
						for (int i = 0; i < prefix.length(); i++) {
							prefixList.insert(Character.toString(prefix.charAt(i)), i);
						}

						// your recursion
						postfix = prefixToPostfix(prefixList).toString();
						
						if (prefixValid) { // check one final time if prefix was valid but not for entire string
							if (postfix.length() < prefix.length()) {
								prefixValid = false;
								closedIndex = prefix.length() - postfix.length() - 1;
								errorMessage = "prefix prematurely closed at index: " + closedIndex + " (operand: "+prefix.charAt(closedIndex)+")";
							}
						}
					}
					
					documentResults(prefix, postfix);
					
					// reset to true
					prefixValid = true; 
					errorMessage = "prefix is invalid";
				}
			}
		}
	}

	/**
	 * Recursively converts prefix expression to postfix using recursion
	 * 
	 * prefix expression will have form: [{operator}, {operand}, {operand}]
	 * 
	 * @param exp
	 * @return
	 * @throws Exception
	 */
	public static ListLinked prefixToPostfix(ListLinked prefixList) throws Exception {
		String op = prefixList.remove(0);
		ListLinked op1 = new ListLinked();
		ListLinked op2 = new ListLinked();
		ListLinked output = new ListLinked();
		
		if (prefixValid && prefixList.size != 0) {
			if (isOperator(prefixList.head.data.charAt(0))) {
				op1 = prefixToPostfix(prefixList);
			} else if (isOperand(prefixList.head.data.charAt(0))) {
				op1.insert(prefixList.remove(0), 0);
			} else {
				prefixValid = false;
				errorMessage = "invalid character: " + prefixList.head.data.charAt(0);
			}
		}
		
		if (prefixValid && prefixList.size != 0) {
			if (isOperator(prefixList.head.data.charAt(0))) {
				op2 = prefixToPostfix(prefixList);
			} else if (isOperand(prefixList.head.data.charAt(0))) {
				op2.insert(prefixList.remove(0), 0);
			} else {
				prefixValid = false;
				errorMessage = "invalid character: " + prefixList.head.data.charAt(0);
			}
		}
		
		if (prefixValid) {
			while (!op1.isEmpty()) {
				output.insert(op1.remove(0), output.size);
			}
			while (!op2.isEmpty()) {
				output.insert(op2.remove(0), output.size);
			}
			output.insert(op, output.size);
		}
		
		return output;
	}

	/**
	 * Writes the prefix/postfix strings to the output file
	 * 
	 * @param prefixString
	 * @param postfixString
	 */
	private static void documentResults(String prefixString, String postfixString) {

		writeStringToFile(outputWriter, "---------------------------------------------");

		// if (end of line) and (prefix string is valid) and (operand stack is empty):
		// write stack to file
		if (prefixValid) {
			String line = prefixString + " converts to postfix: " + postfixString;
			LOGGER.debug(line);
			writeStringToFile(outputWriter, line + "\n");

		} else {
			// write INVALID to output file for given prefix string
			String line = "prefix "+ prefixString +" could not be converted: " + errorMessage;
			LOGGER.error(line);
			writeStringToFile(outputWriter, line + "\n");

		}
	}

	/**
	 * Appends the provided string to file
	 * 
	 * @param outputStream
	 * @param line
	 */
	private static void writeStringToFile(BufferedWriter outputWriter, String line) {

		try {
			outputWriter.append(line);
			outputWriter.newLine();
		} catch (IOException e) {
			LOGGER.fatal("caught i/o error while appending string (" + line + ") to file.");
			e.printStackTrace();
		}

	}

	/**
	 * Returns true if character is an operand (if alphabet letter)
	 * 
	 * @param c
	 * @return isOperand
	 */
	private static boolean isOperand(char c) {
		if (Character.isLetter(c)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if character is operator
	 * 
	 * @param c
	 * @return isOperator
	 */
	private static boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '$' || c == '^') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if character is whitespace
	 * 
	 * @param cInt
	 * @return
	 * @throws Exception
	 */
	private static boolean isWhiteSpace(int cInt) throws Exception {

		char c = (char) cInt;
		if (!String.valueOf(cInt).matches(".") && !String.valueOf(c).equals(" ")) {
			return false;
		} else {
			return true;
		}
	}

}
