package scorpion_solitaire;

import lists.*;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

public class Play {

	private static Logger LOGGER = LogManager.getLogger(Play.class);

	private static FileReader inputReader = null;
	private static BufferedWriter outputWriter = null;
	
	private static GameCardsColumn[] board; // Array of ListLinked objects holding Card objects
	private static ReserveCards reserve; // ListLinked object holding 3 or 0 Card objects

	public static void main(String[] args) throws Exception {
		
		
		// validate args
		if (args.length != 1) {
			String msg = "you must provide exactly 1 input args: inputFile.";
			throw new Exception(msg);
		}

		String inputFile = args[0];

		LOGGER.info("reading user inputs");
		LOGGER.debug("input file: " + inputFile);

		try {
			inputReader = new FileReader(inputFile); // input file

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
		
		String line;
		try (BufferedReader br = new BufferedReader(inputReader)) {
			LOGGER.info("Reading input file.");
			
			String dealLine;
			while ((line = br.readLine()) != null) { // read in file until readLine is found
				if (validLine(line)) { // validate line
					
					dealLine = line;
					
					// TODO: remove white space from line
					// TODO: make characters are all uppercase
					
					break;
				}
			}
			
			// TODO: deal cards
			/*
			 * 1. Read through each cardString
			 * 2. initialize Card object
			 * 3. append to appropriate GameCardsColumn linked list in 'board' Array
			 * 4. once reach final 3 cards, add these to the ReserveCards linked list
			 */
		}
	}



	/**
	 * 
	 * TODO: complete defintion
	 * 
	 * @param line
	 * @return
	 */
	private static boolean validLine(String line) {
		
		boolean validLine;
		if (!line.isBlank() && !line.isEmpty()) { // only process lines that contain non-whitespace
			validLine = false;
		} else {
			
			// TODO: extra validation
			
			validLine = true;
		}
		
		return validLine;
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
