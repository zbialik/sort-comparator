package scorpion_solitaire;

import lists.*;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

public class Play {

	private static Logger LOGGER = LogManager.getLogger(Play.class);

	private static FileReader inputReader = null;

	private static ListLinked[] board = createBoard(); // Array of ListLinked objects holding Card objects
	private static ListLinked reserve = new ListLinked(); // ListLinked object holding 3 or 0 Card objects

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

			dealCards(inputReader); // deals board and reserve cards based on input file

			printBoardLayout();

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
			} catch (IOException e) {
				LOGGER.error("error closing stream.");
			}
		}
	}

	/**
	 * TODO: add documentation
	 * 
	 * @return
	 */
	private static ListLinked[] createBoard() {
		ListLinked[] board = new ListLinked[7]; // Array of ListLinked objects holding Card objects

		for (int i = 0; i < board.length; i++) {
			board[i] = new ListLinked();
		}

		return board;
	}

	/**
	 * prints an outline of the current board layout
	 * 
	 * @throws Exception
	 */
	private static void printBoardLayout() throws Exception {

		// find the max # of rows out of all columns in game board
		int maxRows = 0;
		for (int i = 0; i < board.length; i++) {
			if (maxRows < board[i].size) {
				maxRows = board[i].size;
			}
		}

		String[] cardsRow = new String[7];
		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < board.length; j++) { // grab all card strings

				if (board[j].size <= j) { // just print empty string
					cardsRow[j] = "";
				} else {
					cardsRow[j] = board[j].getListNode(i).data.toString();
				}
			}

			// print cards row
			for (int j = 0; j < cardsRow.length; j++) {
				System.out.printf("%4s", cardsRow[j]);
			}
			System.out.println();

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
	private static void dealCards(FileReader inputReader) throws IOException, Exception {

		String line;
		try (BufferedReader br = new BufferedReader(inputReader)) {
			LOGGER.info("Reading input file.");

			String dealLine = "";
			LOGGER.info("Begin reading input file char by char.");
			int cInt;
			while ((cInt = inputReader.read()) != -1) { // read and process one character
				char c = (char) cInt;
				dealLine += c;
			}

			// split dealt line into array of Strings representing each Card in order
			// provided
			String[] cards = dealLine.split("\\s+");

			/*
			 * Read through each card and do the following: 1. initialize Card object 2.
			 * append to appropriate "column" in 'board' Array OR add to reserve
			 */
			Card card = new Card();
			int j = 0; // use j to track column a card is being appended to
			for (int i = 0; i < cards.length; i++) {

				card.value = getCardValue(cards[i]); // grab card value (int)
				card.suit = getCardSuit(cards[i]); // grab suit value (char)

				if (i < cards.length - 3) { // append to board cards

					// cards dealt to first 4 columns AND first 3 rows are faceDown
					if (board[j].size < 3 && j < 4) {
						card.faceUp = false;
					} else {
						card.faceUp = true;
					}

					board[j].append(card); // append card

					// move to next column (or wrap to first column)
					if (j == 6) {
						j = 0; // start again at first column
					} else {
						j++; // increment to append next card to next column
					}

				} else { // append to reserve cards
					card.faceUp = false;
					reserve.append(card);
				}
			}

		}
	}

	private static char getCardSuit(String string) {
		// TODO FINISH LOGIC
		return 'c';
	}

	private static int getCardValue(String string) {
		// TODO FINISH LOGIC
		return 5;
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
