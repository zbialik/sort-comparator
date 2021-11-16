package scorpion_solitaire;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lists.ListLinked;
import lists.ListNode;

public class CardDeck {

	private static Logger LOGGER = LogManager.getLogger(CardDeck.class);
	private TableauColumnListLinked cards;
	public TableauColumnListLinked[] tableau = createBoard(); // Array of TableauColumnListLinked objects holding Card objects
	public TableauColumnListLinked reserve = new TableauColumnListLinked(); // TableauColumnListLinked object holding 3 or 0 Card objects

	public CardDeck() {
	}
	
	public CardDeck(TableauColumnListLinked cards) {
		this.cards = cards;
	}
	
	public CardDeck(TableauColumnListLinked[] tableau, TableauColumnListLinked reserve) {
		this.cards = new TableauColumnListLinked();
		this.tableau = tableau;
		this.reserve = reserve;
	}
	
	
	/**
	 * shuffles the deck of cards
	 * @throws Exception 
	 */
	public void shuffle() throws Exception {
		
		TableauColumnListLinked shuffledCards = new TableauColumnListLinked(); // initialize shuffledCards to set for deck
		Random rand = new Random(); // initialize random-number generator
		Card card;
		int index;
		while (!this.cards.isEmpty()) { 
			// select random number betweeen 0 and cards.size-1
			index = rand.nextInt(this.cards.size);
			
			// remove that node and grab its data
			card = (Card) this.cards.remove(index);
			
			// append to shuffledCards
			shuffledCards.append(card);
			
		}
		
		this.cards = shuffledCards;
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
	public static boolean checkWin(CardDeck currDeck) throws Exception {
		boolean gameWon;
		TableauColumnListLinked[] currTableau = getTableauClone(currDeck.tableau);

		int winningPiles = 0;

		TableauColumnListLinked col;
		for (int i = 0; i < currTableau.length; i++) { // loop through each column
			col = currTableau[i];
			if (checkWinPile(col)) {
				winningPiles++;
			}
		}

		if (winningPiles == 4) {
			LOGGER.trace("number of winning piles equal 4 - the game has been won");
			gameWon = true;
		} else if (winningPiles > 4) {
			String msg = "number of winning piles exceeds what is possible (" + winningPiles + " > 4).";
			LOGGER.error(msg);
			throw new Exception(msg);
		} else {
			LOGGER.trace("number of winning piles is less than 4 - the game continues");
			gameWon = false;
		}

		return gameWon;
	}
	
	/**
	 * Returns the number of faceUp cards in the provided tableau
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static int countFaceUpCards(CardDeck currDeck) throws Exception {
		
		// faceDown cards (and we know where these would reside)
		int faceUpCount;
		if(currDeck.reserve.isEmpty()) {
			faceUpCount = 52;
		} else {
			faceUpCount = 49;
		}
		
		
		TableauColumnListLinked col;
		ListNode cardNode;
		for (int i=0; i < 4; i++) {
			col = currDeck.tableau[i];
			cardNode = col.head;
			
			while (cardNode != null && !((Card) cardNode.data).faceUp) {
				faceUpCount--; // decrement count for every face down card found
				cardNode = cardNode.next; // move to next card
			}
		}
		
		return faceUpCount;
		
	}
	
	/**
	 * Returns the number of tableau piles containing Ace at bottom
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static int countAceTails(CardDeck currDeck) throws Exception {
		
		// count aces for each tail of 
		int aceCount = 0;
		
		ListNode tail;
		Card tailCard;
		for (int i=0; i < currDeck.tableau.length; i++) {
			
			tail = currDeck.tableau[i].tail;
			
			if (tail != null) {
				tailCard = (Card) tail.data;
				
				if (tailCard.value == 1) {
					aceCount++;
				}
			}
		}
		
		return aceCount;
		
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
	private static boolean checkWinPile(TableauColumnListLinked col) throws Exception {

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

				if (currCard.value == nextCard.value - 1) { // 1. verify nextCard has value minus 1
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
	private static TableauColumnListLinked[] createBoard() {
		TableauColumnListLinked[] board = new TableauColumnListLinked[7]; // Array of TableauColumnListLinked objects holding Card objects
	
		for (int i = 0; i < board.length; i++) {
			board[i] = new TableauColumnListLinked();
		}
	
		return board;
	}

	/**
	 * Distributes the reserve to the 3 left-most piles
	 * 
	 * @throws Exception
	 */
	public static CardDeck distributeReserve(CardDeck deck) throws Exception {
		
		CardDeck newDeck = deck.clone();
		int col = 0;
		while (!deck.reserve.isEmpty()) {

			Card tmp = (Card) deck.reserve.remove(0);
			tmp.faceUp = true;
			newDeck.tableau[col].append(tmp); // append reserve card to pile

			col++; // goto next column
		}
		
		newDeck.reserve.clear();
		
		return newDeck;

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
	public void dealCards() throws Exception {

		/*
		 * Read each card and append to appropriate "column" in 'board' Array OR add to reserve
		 */
		int j = 0; // use j to track column a card is being appended to
		while (!cards.isEmpty()) {

			Card card = (Card) cards.remove(0); // remove card from deck
			if (cards.size > 2) { // append to board cards

				// cards dealt to first 4 columns AND first 3 rows are faceDown
				if (this.tableau[j].size < 3 && j < 4) {
					card.faceUp = false;
				} else {
					card.faceUp = true;
				}

				this.tableau[j].append(card); // append card

				// move to next column (or wrap to first column)
				if (j == 6) {
					j = 0; // start again at first column
				} else {
					j++; // increment to append next card to next column
				}

			} else { // append to reserve cards
				card.faceUp = false;
				this.reserve.append(card);
			}
		}
	}

	/**
	 * Helper method for returning list of possible moves (moves are arrays of TableauColumnListLinked objects)
	 * 
	 * @param clone
	 * @return
	 * @throws Exception
	 */
	public static TableauColumnListLinked getPossibleMoves(CardDeck deck) throws Exception {
		CardDeck move = deck.clone();
		ListNode curr;
		ListNode tailPile;
		TableauColumnListLinked possibleMoves = new TableauColumnListLinked();
		TableauColumnListLinked[] currTableau = getTableauClone(deck.tableau);
		
		int currIndex;
		for (int i = 0; i < currTableau.length; i++) { // append all tail card nodes

			tailPile = currTableau[i].tail; // grab tail for column (NOTE: could be null)

			/*
			 * iterate over every card in tableau
			 * 
			 * for each card that can be appended to tailCardNode, score the move
			 */
			for (int j = 0; j < currTableau.length; j++) {
				if (i != j) { // not possible to move cards to same column
					curr = currTableau[j].head;
					currIndex = 0;

					while (curr != null) {
						
						if (isValidMove(curr, tailPile)) { // if a possible move, score it
							move.tableau = getResultingTableau(getTableauClone(deck.tableau), j, currIndex, i); 
							possibleMoves.append(move.clone());
							LOGGER.trace("possible move: " + Arrays.toString(move.tableau));
						}

						curr = curr.next; // goto next card
						currIndex++; // increment currIndex
					}
				}
			}
		}
		
		return possibleMoves;
	}
	
	
	/**
	 * 
	 * Helper method for returning array of lists that hold the following data:
	 * 	[0]: original column/pile receiving the sublist of cards(s)
	 * 	[1]: original column/pile sending the sublist of cards(s)
	 * 	[2]: a sublist of the pile being moved
	 * 
	 * @param clone
	 * @return
	 * @throws Exception
	 */
	public static TableauColumnListLinked[] getChangedPiles(CardDeck currDeck, CardDeck newDeck) throws Exception {
		
		TableauColumnListLinked[] output = new TableauColumnListLinked[3];
		
		TableauColumnListLinked[] currTableau = getTableauClone(currDeck.tableau);
		TableauColumnListLinked[] newTableau = getTableauClone(newDeck.tableau);
		boolean foundPileReceiver = false;
		boolean foundPileShipper = false;
		int i = 0;
		while (!foundPileShipper || !foundPileReceiver) {
			
			if (currTableau[i].size < newTableau[i].size) { // found receiver
				foundPileReceiver = true;
				output[0] = currTableau[i];
			} else if (currTableau[i].size > newTableau[i].size) { // found shipper
				foundPileShipper = true;
				output[1] = currTableau[i];
				int diff = currTableau[i].size - newTableau[i].size;
				TableauColumnListLinked sublist = currTableau[i].clone();
				while (sublist.size != diff) {
					sublist.remove(0); // keep removing head until sublist is same size as diff
				}
				output[2] = sublist;
			}
			
			i++;
		}
		
		return output;
	}
	
	/**
	 * Returns a list of all face up cards
	 * 
	 * @return
	 * @throws Exception 
	 */
	public ListLinked getFaceUpTableauCards() throws Exception {
		ListLinked list = new ListLinked();
		ListNode curr;
		
		for (int i = 0; i < this.tableau.length; i++) {
			curr = this.tableau[i].head;
			
			while (curr != null) {
				if (((Card) curr.data).faceUp) {
					list.append((Card) curr.data);
				}
				
				curr = curr.next;
			}
		}
		
		return list;
	}

	/**
	 * Returns the tableau if the provided move were to be implemented
	 * 
	 * @param currTableau
	 * @param currColumn
	 * @param currIndex
	 * @param tailColumn
	 * @return newTableau
	 * @throws Exception
	 */
	public static TableauColumnListLinked[] getResultingTableau(TableauColumnListLinked[] currTableau, int currColumn, int currIndex, int tailColumn) throws Exception {

		// copy tableau to ensure we don't update real tableau
		TableauColumnListLinked[] newTableau = getTableauClone(currTableau);

		// get curr and tail nodes
		TableauColumnListLinked currPile = newTableau[currColumn];
		TableauColumnListLinked tailPile = newTableau[tailColumn];
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
	 * Helper method for returning a copy of the tableau array
	 * 
	 * @param tableau
	 * @return cloneTableau
	 */
	public static TableauColumnListLinked[] getTableauClone(TableauColumnListLinked[] currTableau) {
		
		TableauColumnListLinked[] cloneTableau = new TableauColumnListLinked[7];
		for (int i=0; i < currTableau.length; i++) {
			cloneTableau[i] = currTableau[i].clone();
		}
		
		return cloneTableau;
	}
	
	/**
	 * Verifies if the move is valid given conditions for Scorpion Solitaire
	 * 
	 * @param cardNode
	 * @param tailNode
	 * @return validMove
	 */
	static boolean isValidMove(ListNode cardNode, ListNode tailNode) {
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
	public static void printBoardLayout(CardDeck currDeck) throws Exception {

		TableauColumnListLinked[] currTableau = currDeck.tableau;
		
		// find the max # of rows out of all columns in game board
		int maxRows = 0;
		for (int i = 0; i < currTableau.length; i++) {
			if (maxRows < currTableau[i].size) {
				maxRows = currTableau[i].size;
			}
		}
		
		String[] cardsRow = new String[currTableau.length];

		System.out.println("--------------------------------------------------------");
		System.out.println("Reserve: " + currDeck.reserve.toString() + "\n");
		System.out.println("Tableau:\n");
		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < currTableau.length; j++) { // grab all card strings

				if (currTableau[j].size <= i) { // just print empty string
					cardsRow[j] = "";
				} else {
					cardsRow[j] = currTableau[j].getListNode(i).data.toString();
				}
			}

			// print cards
			for (int j = 0; j < cardsRow.length; j++) {
				System.out.printf("%8s", cardsRow[j]);
			}

			System.out.println();

		}
		System.out.println();
	}
	
	
	/**
	 * Returns clone of this object
	 * 
	 */
	public CardDeck clone() {
		CardDeck tmp = new CardDeck(); // allocate space

		tmp.cards = this.cards.clone();
		tmp.tableau = getTableauClone(this.tableau);
		tmp.reserve = this.reserve.clone();
		
		return tmp;
	}
	
	/**
	 * Returns String representation of this CardDeck
	 * 
	 */
	public String toString() {
		
		String output = "";
		
		output += "reserve: " + this.reserve.toString();
		output += "\ntableau:" + Arrays.toString(this.tableau) + "\n";
		
		return output;
	}
	
}
