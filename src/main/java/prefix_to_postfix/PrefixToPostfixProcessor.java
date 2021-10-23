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
			convertPrefixToPostfix(inputReader, outputWriter); // execute main process

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

	private static void convertPrefixToPostfix(FileReader inputReader, BufferedWriter outputWriter) {
		// TODO complete logic
		
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
	 * @return postfixValid (bool) representing whether postfix string is valid
	 * @throws Exception
	 */
	private static boolean processCharacter(int cInt) throws Exception {

		char c = (char) cInt;
		boolean postfixValid = true;

		return postfixValid;

	}

	/**
	 * Returns the string representing the given operator in the machine lang e.g.
	 * '+' returns "AD"
	 * 
	 * @param operator
	 * @return machineOperator
	 * @throws Exception
	 */
	private static String translateOperator(char operator) throws Exception {

		// return translated operators for +, -, *, and /
		if (operator == '+') {
			return "AD";
		} else if (operator == '-') {
			return "SB";
		} else if (operator == '*') {
			return "ML";
		} else if (operator == '/') {
			return "DV";
		} else if (operator == '$' || operator == '^') {
			return "PW";
		} else {
			throw new Exception("the provided character is not a valid operator");
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

	private static boolean isWhiteSpace(int cInt) throws Exception {

		char c = (char) cInt;
		if (!String.valueOf(cInt).matches(".") && !String.valueOf(c).equals(" ")) {
			return false;
		} else {
			return true;
		}
	}

}
