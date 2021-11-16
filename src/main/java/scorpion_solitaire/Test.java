package scorpion_solitaire;

import lists.*;

import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;

public class Test {

	private static final int LAYERS = 5;

	private static Logger LOGGER = LogManager.getLogger(Play.class);

	private static FileReader inputReader = null;

	public static CardDeck deck;

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
			TableauColumnListLinked cards = readCards(inputReader); // reads cards based on input file

			deck = new CardDeck(cards);
			deck.shuffle(); // shuffle provided card sequence
			
			deck.dealCards(); // deals to deck.tableau and deck.reserve
			
			CardDeck.printBoardLayout(deck); // print out game layout
			
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
		int turns = 0;

		CardDeck nextDeck; // Array of ListLinked objects holding Card objects
		
		// UPDATE TABLEAU TO BE WIN
		deck.reserve.clear();
		for (int i = 0; i < deck.tableau.length; i++) { // clear tableau
			deck.tableau[i].clear();
		}

		char[] suits = {'C', 'H', 'D', 'S'};
		int i = 0;
		for (char suit : suits) { // create winning tableau
			i++;
			for (int val = 13; val >= 1; val--) {
				deck.tableau[i].append(new Card(val, suit, true));	
			}
		}

		while (!gameWon && !gameLost) {
			turns++;

			// get best move
			nextDeck = selectMove(deck.clone());

			// need to make sure we verify we didn't win game in previous loop
			if (nextDeck == null && !gameWon) { 
				if (deck.reserve.isEmpty()) {
					LOGGER.debug("no possible moves left AND reserve is empty - the game has been lost.");
					gameLost = true;
				} else {
					LOGGER.debug("no possible moves left AND reserve is full - distributing reserve now.");
					deck = CardDeck.distributeReserve(deck);
				}
			} else {
				deck = nextDeck; // make move
			}

			// check win status
			gameWon = CardDeck.checkWin(deck);

			// print out game layout
			CardDeck.printBoardLayout(deck);

		}

		// print out final statements for computer winning/losing the game
		System.out.println();
		if (gameWon) {
			System.out.println("Computer WON in Scorpion Solitaire ("+turns+" turns)!");
		} else {
			System.out.println("Computer LOST in Scorpion Solitaire ("+turns+" turns).");
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

	private static TableauColumnListLinked readCards(FileReader inputReader) throws Exception {

		TableauColumnListLinked deck = new TableauColumnListLinked();

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
	 * @param score
	 * @param curr
	 * @param tailPile
	 * @return score
	 * @throws Exception 
	 */
	private static double scoreMove(int recurseLayer, double score, CardDeck currD, CardDeck newD) throws Exception {		
		
		recurseLayer++;
		CardDeck possibleMove;
		CardDeck currDeck = currD.clone();
		CardDeck newDeck = newD.clone();
		
		LOGGER.debug("scoreMove() recurse layer: " + recurseLayer + " (input score: "+ score +")");
		LOGGER.trace("currDeck:\n" + currDeck.toString());
		LOGGER.trace("newDeck:\n" + newDeck.toString());

		if (recurseLayer < LAYERS) {
			
			// if move can lead to win, set score to very large number and return
			if (CardDeck.checkWin(newDeck)) {
				score = Double.POSITIVE_INFINITY; // make score extremely large due to winning tableau and return;
			} else {
				
				// otherwise, score the individual move and then recursively score possible moves after this one
				// NOTE: recursion ends when move found is a winner OR we've reached LAYERS constant
				score += scoreMoveSingle(currDeck, newDeck);
				
				ListLinked possibleMoves = CardDeck.getPossibleMoves(newDeck); // get list of moves after this one
				ListNode possibleMoveNode = (ListNode) possibleMoves.head;
				double submoveScore = 0d; // used to calculate avg of scores possible for submoves
				double submoveScoreBest = 0d; // used to calculate avg of scores possible for submoves
				while (possibleMoveNode != null) { // loop over each possible move
					
					possibleMove = (CardDeck) possibleMoveNode.data;
					submoveScore = scoreMove(recurseLayer, score, newDeck, possibleMove); // calculate submoveScore recursively
					if (submoveScore > submoveScoreBest) {
						submoveScoreBest = submoveScore;
					}
					
					possibleMoveNode = possibleMoveNode.next;
				}
				
				score += submoveScoreBest; // add best submove score
			}
		}
		LOGGER.debug("scoreMove() recurse layer: " + recurseLayer + " (output score: "+ score +")");
		return score;
	}
	
	/**
	 * Scores a move based on custom logic/priorities 
	 * 	- this is where the magic happens :) #businessLogic
	 * 
	 * NOTES: 
	 * 	- used within recursive function scoreMove()
	 * 	- scoreInc is mean to be multipled by some factor and added to score
	 * 
	 * @param currDeck
	 * @param newDeck
	 * @return score
	 * @throws Exception 
	 */
	private static double scoreMoveSingle(CardDeck currDeck, CardDeck newDeck) throws Exception {
		
		double score = 100.0d;
		double scoreInc = 1.0d; 
		
		int currCount;
		int newCount;

		// CASE A - GOOD: ADD 1000 scoreInc when faceUp cards increases
		currCount = CardDeck.countFaceUpCards(currDeck);
		newCount = CardDeck.countFaceUpCards(newDeck);
		if (currCount != newCount) {
			score += 1000 * scoreInc; // add 1000 times the scoreInc
		}
		
		// CASE B - BAD: MINUS x50 scoreInc when Ace is revealed as tail
		currCount = CardDeck.countAceTails(currDeck);
		newCount = CardDeck.countAceTails(newDeck);
		if (currCount != newCount) {
			score -= 50 * scoreInc; // minus 50 times the scoreInc
		}
		
		// CASE C - BAD: MINUS x100 scoreInc when card is revealed as tail
		// and there are no faceUp cards that can append to it
		TableauColumnListLinked[] changedPilesList = CardDeck.getChangedPiles(currDeck, newDeck);
		TableauColumnListLinked origReceivingList = changedPilesList[0];
		TableauColumnListLinked origShippingList = changedPilesList[1];
		TableauColumnListLinked appendingList = changedPilesList[2];
		ListNode curr = origShippingList.tail;
		ListNode appending = appendingList.head;
		while (appending != null) {
			curr = curr.prev;
			appending = appending.next;
		}
		// check if any faceUp cards are valid to append to curr
		ListLinked list = currDeck.getFaceUpTableauCards();
		boolean newTailAppendable = false;
		for (int i = 0; i < list.size; i++) {
			if (CardDeck.isValidMove(list.getListNode(i), curr)) {
				newTailAppendable = true;
			}
		}
		if (!newTailAppendable) {
			score -= 100 * scoreInc; // minus 100 times the scoreInc
		}
		
		
		// CASE D - BAD: MINUX 10 scoreInc when the target pile has the card
		// needed to append the appending sublist's tail
		curr = origReceivingList.head;
		appending = appendingList.head;
		while (curr != null) {
			while (appending != null) {
				if (CardDeck.isValidMove(appending, curr)) {
					score -= 10 * scoreInc; // minus 10 times the scoreInc
				}
				appending = appending.next;
			}
			
			curr = curr.next;
		}
		
		return score;
	}

	/**
	 * Identifies best move to execute for current tableau and returns it.
	 * If no moves are possible, the method returns a null pointer.
	 * 
	 * @return bestMove
	 * @throws Exception
	 */
	private static CardDeck selectMove(CardDeck currDeck) throws Exception {

		CardDeck bestMove = null;
		CardDeck possibleMove;

		// for each column tail, score each possible move to iteratively determine bestMove
		double bestScore = Double.NEGATIVE_INFINITY; // start with a very low score
		double currScore = Double.NEGATIVE_INFINITY; // start with a very low score
		
		ListLinked possibleMoves = CardDeck.getPossibleMoves(currDeck);
		
		ListNode possibleMoveNode = (ListNode) possibleMoves.head;
		while (possibleMoveNode != null) { // loop over each possible move
			
			possibleMove = (CardDeck) possibleMoveNode.data;
			
			currScore = scoreMove(0, 0d, currDeck, possibleMove); // recursively finds best move

			// check if null to ensure the move still gets chosen even if its really bad
			if (bestMove == null || bestScore < currScore) { // if better score, set bestMove to resulting tableau
				bestMove = possibleMove;
				bestScore = currScore;
			}
			possibleMoveNode = possibleMoveNode.next;
		}

		return bestMove;
	}

}
