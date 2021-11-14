package scorpion_solitaire;

import lists.*;

import org.apache.commons.lang3.StringUtils;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;

public class Play {

	private static Logger LOGGER = LogManager.getLogger(Play.class);

	private static FileReader inputReader = null;

	private static CardDeck deck;

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
			ListLinked cards = readCards(inputReader); // reads cards based on input file

			deck = new CardDeck(cards);
			
			// TODO: IMPLEMENT SHUFFLE DECK
			// deck.shuffle();
			
			deck.dealCards(); // deals to deck.tableau and deck.reserve

			autoplay();

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
	 * Primary method for automating the decisions made to
	 * play the game. The method will continuously loop and make game moves
	 * based on the logical rulesets. The loop is exited/completed when either
	 * of the following occurs:
	 * 
	 * - tableau cards are empty (win the game)
	 * - no more possible moves left (lose the game)
	 * 
	 * @throws Exception
	 */
	private static void autoplay() throws Exception {
		boolean gameWon = false;
		boolean gameLost = false;

		ListLinked[] move; // Array of ListLinked objects holding Card objects

		while (!gameWon && !gameLost) {
			
			// print out game layout
			printBoardLayout();

			// get best move
			move = selectMove();

			if (move == null) {
				gameLost = checkLoss(); // check if there is reserve , other wise we lost
			} else {
				deck.tableau = move; // make move
			}

			// check win status
			gameWon = checkWin();
			
		}

		// print out final statements for computer winning/losing the game
		System.out.println();
		if (gameWon) {
			System.out.println("Computer WON in Scorpion Solitaire!");
		} else {
			System.out.println("Computer LOST in Scorpion Solitaire.");
		}

	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	private static boolean checkLoss() throws Exception {
		boolean gameLost;

		if (deck.reserve.isEmpty()) {
			LOGGER.info("no possible moves left AND reserve is empty - the game has been lost.");
			gameLost = true;
		} else {
			LOGGER.info("no possible moves left AND reserve is full - distributing reserve now.");
			distributeReserve();
			gameLost = false;
		}
		return gameLost;
	}

	/**
	 * Returns true if the game has been won and false otherwise.
	 * 
	 * Game is won when there are 4 separate piles in the tablea
	 * that each contain the Card objects in-order for each suit, respectively
	 * Additionally, all card objects must be faceUp.
	 * 
	 * @return gameWon
	 * @throws Exception
	 */
	private static boolean checkWin() throws Exception {
		boolean gameWon;

		int winningPiles = 0;

		ListLinked col;
		for (int i = 0; i < deck.tableau.length; i++) { // loop through each column
			col = deck.tableau[i];
			if (checkWinPile(col)) {
				winningPiles++;
			}
		}

		if (winningPiles == 4) {
			LOGGER.debug("number of winning piles equal 4 - the game has been won");
			gameWon = true;
		} else if (winningPiles > 4) {
			String msg = "number of winning piles exceeds what is possible (" + winningPiles + " > 4).";
			LOGGER.error(msg);
			throw new Exception(msg);
		} else {
			LOGGER.debug("number of winning piles is less than 4 - the game continues");
			gameWon = false;
		}

		return gameWon;
	}

	/**
	 * Returns true if the provided pile meets needed conditions to
	 * be considered a winning pile. The conditions are as follows:
	 * - the list is size 13
	 * - each card is faceUp
	 * - each card is the same suit
	 * - the card values are in-order
	 * 
	 * @param col
	 * @return
	 * @throws Exception
	 */
	private static boolean checkWinPile(ListLinked col) throws Exception {

		ListNode node;
		Card currCard;
		Card nextCard;

		boolean winPile = true;  // set to true but set to false if conditions aren't satisfied

		if (col.size == 13) { // first check that this pile has size 13
			for (int j = 0; j < col.size - 1; j++) { // loop and check that cardNext has needed characteristics
				node = col.getListNode(j);
				currCard = (Card) node.data;
				nextCard = (Card) node.next.data;

				if (!currCard.faceUp) { // 1. verify faceUp
					winPile = false;
				}

				if (currCard.suit != nextCard.suit) { // 1. verify nextCard has same suit as currCard
					winPile = false;
				}

				if (currCard.value == nextCard.value + 1) { // 1. verify nextCard has value incremented by 1
					winPile = false;
				}
			}
		} else {
			winPile = false;
		}

		return winPile;
	}

	/**
	 * Initializes board array which holds the game objects containing
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

//	/**
//	 * Reads in the input file line by line and converts each prefix expression to
//	 * postfix and writes to the output file
//	 * 
//	 * @param inputReader
//	 * @param outputWriter
//	 * @throws IOException
//	 * @throws Exception
//	 */
//	private static void dealCards(CardDeck deck) throws IOException, Exception {
//
//		/*
//		 * Read each card and append to appropriate "column" in 'board' Array OR add to reserve
//		 */
//		int j = 0; // use j to track column a card is being appended to
//		while (!deck.isEmpty()) {
//
//			Card card = (Card) deck.remove(0); // remove card from deck
//			if (deck.size > 2) { // append to board cards
//
//				// cards dealt to first 4 columns AND first 3 rows are faceDown
//				if (tableau[j].size < 3 && j < 4) {
//					card.faceUp = false;
//				} else {
//					card.faceUp = true;
//				}
//
//				tableau[j].append(card); // append card
//
//				// move to next column (or wrap to first column)
//				if (j == 6) {
//					j = 0; // start again at first column
//				} else {
//					j++; // increment to append next card to next column
//				}
//
//			} else { // append to reserve cards
//				card.faceUp = false;
//				reserve.append(card);
//			}
//		}
//	}

	/**
	 * Distributes the reserve to the 3 left-most piles
	 * 
	 * @throws Exception
	 */
	private static void distributeReserve() throws Exception {

		int col = 0;
		while (!deck.reserve.isEmpty()) {

			Card tmp = (Card) deck.reserve.remove(0);
			tmp.faceUp = true;
			deck.tableau[col].append(tmp); // append reserve card to pile

			col++; // goto next column
		}

	}

	/**
	 * Provided the string representation of the card, returns the character
	 * representing the suit
	 * 
	 * assumption: card suit is the last character in the string
	 * 
	 * @param string
	 * @return suit
	 */
	private static char getCardSuit(String cardInput) {
		// card suit is the 2nd character
		return cardInput.charAt(cardInput.length() - 1);
	}

	/**
	 * Provided the string representation of the card, returns the integer
	 * representing the value
	 * 
	 * assumption:
	 * - card value is all character in the string except the last one
	 * - if input string has length=3, value is assumed to be 10
	 * 
	 * @param string
	 * @return value
	 */
	private static int getCardValueInt(String cardInput) {
		// card suit is the 1st character
		char valueChar = cardInput.charAt(0);

		if (valueChar == 'K') {
			return 13;
		} else if (valueChar == 'Q') {
			return 12;
		} else if (valueChar == 'J') {
			return 11;
		} else if (valueChar == 'A') {
			return 1;
		} else {
			// if input has length 3, assume the card value is 10
			if (cardInput.length() == 3) {
				return 10;
			} else {
				return Character.getNumericValue(valueChar);
			}
		}
	}
	
	/**
	 * Returns the tableau if the provided move were to be implemented
	 * 
	 * @param curr
	 * @param tailPile
	 * @return newTableau
	 * @throws Exception 
	 */
	private static ListLinked[] getResultingTableau(int currColumn, int currIndex, int tailColumn) throws Exception {
		// copy tableau to ensure we don't update real tableau
		ListLinked[] newTableau = createBoard();
		
		for (int i=0; i < newTableau.length; i++) {
			newTableau[i] = deck.tableau[i].clone();
		}
		
		// get curr and tail nodes
		ListLinked currPile = newTableau[currColumn];
		ListLinked tailPile = newTableau[tailColumn];
		ListNode currNode = currPile.getListNode(currIndex);
		ListNode tailNode = tailPile.tail; // NOTE: this can be null
		
		int sublistSize = currPile.size - currIndex;
		
		if (tailNode == null) {
			tailPile.head = currNode; // set tail list head to currNode
		} else {
			tailNode.next = currNode; // repoint tail's next to curr
		}
		
		if (currNode == currPile.head) {
			currPile.head = null; // clear head
		} else {
			currNode.prev.next = null; // repoint prev node's next to null
		}
		
		tailPile.tail = currPile.tail; // repoint tail list tail to curr list tail
		currPile.tail = currNode.prev; // repoint currPile's tail to curr's prev
		if (currNode.prev != null) {
			((Card) currNode.prev.data).faceUp = true; // ensure previous tail 
		}
		
		currNode.prev = tailNode; // repoint curr's prev to tail
		
		// finally, update currPile and tailColumn size
		tailPile.size += sublistSize;
		currPile.size -= sublistSize;
		
		return newTableau;
	}

	

	/**
	 * Verifies if the move is valid given conditions for Scorpion Solitaire
	 * 
	 * @param cardNode
	 * @param tailNode
	 * @return validMove
	 */
	private static boolean isValidMove(ListNode cardNode, ListNode tailNode) {
		boolean validMove = false;
		
		Card cardToMove = (Card) cardNode.data;
		if (cardToMove.faceUp) { // can't move facedown cards
			if (tailNode == null) { // if empty tail only true if cardToMove is King
				if (cardToMove.value == 13) {
					// NOTE: I do ONLY allow the King to move if it is NOT the head of the list
					if (cardNode.prev != null) { // assume when prev = null, its head node
						validMove = true;
					}
				}
			} else {
				Card tail = (Card) tailNode.data;
				if (cardToMove.suit == tail.suit && cardToMove.value == tail.value - 1) {
					validMove = true;
				}
			}
		}
		
		return validMove;
	}

	/**
	 * prints an outline of the current board layout
	 * 
	 * @throws Exception
	 */
	private static void printBoardLayout() throws Exception {

		// find the max # of rows out of all columns in game board
		int maxRows = 0;
		for (int i = 0; i < deck.tableau.length; i++) {
			if (maxRows < deck.tableau[i].size) {
				maxRows = deck.tableau[i].size;
			}
		}

		String[] cardsRow = new String[deck.tableau.length];

		System.out.println();
		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < deck.tableau.length; j++) { // grab all card strings

				if (deck.tableau[j].size <= i) { // just print empty string
					cardsRow[j] = "";
				} else if ( !((Card) deck.tableau[j].getListNode(i).data).faceUp ) {
					cardsRow[j] = "??";
				} else {
					cardsRow[j] = deck.tableau[j].getListNode(i).data.toString();
				}
			}

			// print cards
			for (int j = 0; j < cardsRow.length; j++) {
				System.out.printf("%8s", cardsRow[j]);
			}

			if (i == 0) { // if first row, also print reserve cards (faceDown)
				for (int j = 0; j < deck.reserve.size; j++) {
					System.out.printf("%8s", deck.reserve.getListNode(j).data.toString());
				}
			}

			System.out.println();

		}
		System.out.println();
	}

	private static ListLinked readCards(FileReader inputReader) throws Exception {
	
		ListLinked deck = new ListLinked();
	
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
			 * Read through each card and do the following:
			 * 1. initialize Card object
			 * 2. append to deck
			 */
			for (int i = 0; i < cards.length; i++) {
				int value = getCardValueInt(cards[i]); // grab card value (int)
				char suit = getCardSuit(cards[i]); // grab suit value (char)
				deck.append(new Card(value, suit, false)); // append to deck faceDown
			}
	
		}
	
		return deck;
	}

	/**
	 * Returns an integer that represents how good a move is for the game
	 * 
	 * NOTE: this is where the complex logic resides and will be how the game
	 * decisions get optimized/improved upon.
	 * 
	 * @param curr
	 * @param tailPile
	 * @return score
	 */
	private static int scoreMove(ListNode curr, ListNode tailPile) {
		 // TODO: implement scoring - determine if we compare based on node references or tableau objects
		int score = 0;
		
		/*
		 * Strategy Tips:
		 * - expose all face-down cards as soon as possible
		 * - avoid blocks created by reverse sequences
		 * - beware of uncovering aces. no cards may be played on aces.
		 * 
		 * My Tips:
		 * - if you can move an entire list from the right
		 * Possible Moves:
		 * - move card at the end of another column (including all cards under card being moved)
		 * - case 1: card moved is beneath a faceDown card
		 * - case 2: card moved is beneath a faceUp card
		 * 
		 * Move impacts:
		 * 1. changes card from faceDown to faceUp (GOOD)
		 * 2. results in reverse sequence (e.g. 9S above 10S) (BAD)
		 * 3. results in a tail of Ace (BAD)
		 */
		
		return score;
	}

	/**
	 * Identifies best move to execute for current tableau and returns it.
	 * If no moves are possible, the method returns a null pointer.
	 * 
	 * @return bestMove
	 * @throws Exception 
	 */
	private static ListLinked[] selectMove() throws Exception {

		ListLinked[] bestMove = null;
		ListLinked[] move;
		
		// for each column tail, score each possible move to iteratively determine bestMove
		ListNode curr;
		ListNode tailPile;
		int currIndex;
		int bestScore = -10000; // start with a very low score
		int currScore = -10000; // start with a very low score
		for (int i = 0; i < deck.tableau.length; i++) { // append all tail card nodes
			
			tailPile = deck.tableau[i].tail; // grab tail for column (NOTE: could be null)
			
			/*
			 * iterate over every card in tableau
			 * 
			 * for each card that can be appended to tailCardNode, score the move
			 */
			for (int j = 0; j < deck.tableau.length; j++) {
				if (i != j) { // not possible to move cards to same column
					curr = deck.tableau[j].head;
					currIndex = 0;
					
					while (curr != null) {
						
						if (isValidMove(curr, tailPile)) { // if a possible move, score it
							
							move = getResultingTableau(j, currIndex, i);
							
							currScore = scoreMove(curr, tailPile);
							
							if (bestScore < currScore) { // if better score, set bestMove to resulting tableau
								bestMove = move;
							}
						}
						
						curr = curr.next; // goto next card
						currIndex++; // increment currIndex
					}
				}
			}
			
		}

		return bestMove;
	}

}
