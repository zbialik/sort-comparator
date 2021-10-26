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
 * outputFile The program will functionally do the following: - ??????
 * 
 * @author zachbialik
 *
 */
public class PrefixToPostfixProcessor {

	private static Logger LOGGER = LogManager.getLogger(PrefixToPostfixProcessor.class);

	private static FileReader inputReader = null;
	private static BufferedWriter outputWriter = null;

	private static boolean prefixValid = true;

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

		String prefix;
		String postfix;

		LOGGER.info("Begin reading input file line by line.");

		try (BufferedReader br = new BufferedReader(inputReader)) {

			String line;
			ListLinked prefixList = new ListLinked();
			
			while ((line = br.readLine()) != null) { // execute conversion for each line

				if (!line.isBlank() && !line.isEmpty()) { // only process lines that contain non-whitespace
					prefix = line.trim(); // remove white space
					for (int i=0; i < prefix.length(); i++) {
						prefixList.insert(Character.toString(prefix.charAt(i)), i);
					}
					
					// your recursion
					postfix = prefixToPostfix(prefixList).toString();
					documentResults(prefix, postfix);
				}
			}
		}
	}
	
	//Use recursion, Time O(n), Space O(n)
    public static ListLinked prefixToPostfix(ListLinked exp) throws Exception {
        String op = exp.remove(0);
        ListLinked op1 = new ListLinked();
        ListLinked op2 = new ListLinked();
        
        if (isOperator(exp.head.data.charAt(0))) {
        	op1 = prefixToPostfix(exp);
        } else {
        	op1.insert(exp.remove(0), 0);
        }
        
        if (isOperator(exp.head.data.charAt(0))) {
        	op2 = prefixToPostfix(exp);
        } else {
        	op2.insert(exp.remove(0), 0);
        }
        
        ListLinked output = new ListLinked();
        while (!op1.isEmpty()) {
        	output.insert(op1.remove(0), output.size);
        }
        while (!op2.isEmpty()) {
        	output.insert(op2.remove(0), output.size);
        }
        output.insert(op, output.size);
        return output;
    }

	private static void documentResults(String prefixString, String postfixString) {

		writeStringToFile(outputWriter, "---------------------------------------------");

		// if (end of line) and (prefix string is valid) and (operand stack is empty):
		// write stack to file
		if (prefixValid) {
			String line = prefixString + " converts to postfix: " + postfixString;
			LOGGER.debug(line);
			writeStringToFile(outputWriter, line);

		} else {
			// write INVALID to output file for given prefix string
			LOGGER.debug("prefix expression (" + prefixString + "): INVALID.");

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

}
