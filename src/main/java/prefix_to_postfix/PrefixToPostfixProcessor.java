package prefix_to_postfix;

import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

/**
 * This class executes the main method which takes in 2 arguments: inputFile and
 * outputFile The program will functionally do the following: 
 * 		- ??????
 * 
 * @author zachbialik
 *
 */
public class PrefixToPostfixProcessor {

	private static Logger LOGGER = LogManager.getLogger(PrefixToPostfixProcessor.class);

	private static FileReader inputReader = null;
	private static BufferedWriter outputWriter = null;

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

	private static void processFile(FileReader inputReader, BufferedWriter outputWriter) throws IOException, Exception {


		int cInt;
		boolean prefixValid = true;
		boolean prefixConversionComplete = true;
		String prefixString = "";
		String postfixString = "";
		
		LOGGER.info("Begin reading input file char by char.");
		while ((cInt = inputReader.read()) != -1) { // read and process one character
			prefixConversionComplete = false;

			if (!isWhiteSpace(cInt)) {
				
				char c = (char) cInt;

				// append character to postfixString
				prefixString += c;

				// process character
				if (prefixValid) {
					LOGGER.trace("Validating character: " + String.valueOf(c));
					prefixValid = validateCharacter(cInt);
				}

			} // if (end of line)
			else if (String.valueOf((char) cInt).equals("\n") || String.valueOf((char) cInt).equals("\r")) {

				LOGGER.trace("Linebreak character identified - prefix conversion complete");
				
				postfixString = convertPrefixToPostfix(prefixString);

				prefixConversionComplete = true;

			}
			
			if (prefixConversionComplete) { // document results when linebreak is found
				
				LOGGER.debug("documenting results of complete prefix conversion");
				
				documentResults(prefixValid, prefixString, postfixString);
				
				// reset everything to start as new
				prefixString = "";
				postfixString = "";
				prefixValid = true;
			}
		}
	}

	private static String convertPrefixToPostfix(String prefixString) {
		// TODO fill with recursion logic -- see https://www.lavivienpost.com/convert-prefix-to-postfix-code/
		return null;
	}

	private static void documentResults(boolean prefixValid, String prefixString, String postfixString) {
		// TODO complete/validate
		
		writeStringToFile(outputWriter, "---------------------------------------------");

		// if (end of line) and (prefix string is valid) and (operand stack is empty):
		// write stack to file
		if (prefixValid) {

			LOGGER.debug("prefix expression (" + prefixString + "):\t VALID.");

			prefixString += " as postfix string sequence:";
			writeStringToFile(outputWriter, prefixString);
			writeStringToFile(outputWriter, postfixString); 
			
		} else {
			// write INVALID to output file for given prefix string
			LOGGER.debug("prefix expression (" + prefixString + "):\t INVALID.");

			prefixString += " is an INVALID prefix expression\n";
			writeStringToFile(outputWriter, prefixString);

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
	 * If character is operand: push to operandStackLinked return postfixValid =
	 * true Else If character is operator: determine machine language steps and
	 * append to machineLangStackLinked return postfixValid = true Else: return
	 * postfixValid = false
	 * 
	 * @param cInt
	 * @return prefixValid (bool) representing whether prefix is valid
	 * @throws Exception
	 */
	private static boolean validateCharacter(int cInt) throws Exception {

		char c = (char) cInt;
		boolean postfixValid = true;
		
		
		
		

		return postfixValid;

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

	private static boolean isWhiteSpace(int cInt) throws Exception {

		char c = (char) cInt;
		if (!String.valueOf(cInt).matches(".") && !String.valueOf(c).equals(" ")) {
			return false;
		} else {
			return true;
		}
	}

}
