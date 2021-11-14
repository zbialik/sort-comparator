package scorpion_solitaire;

import java.io.IOException;

import lists.ListLinked;

public class CardDeck {
	
	private ListLinked cards;
	public ListLinked[] tableau = createBoard(); // Array of ListLinked objects holding Card objects
	public ListLinked reserve = new ListLinked(); // ListLinked object holding 3 or 0 Card objects

	public CardDeck() {
	}
	
	public CardDeck(ListLinked cards) {
		this.cards = cards;
	}
	
	
	/**
	 * shuffles the deck of cards
	 */
	public void shuffle() {
		// TODO: implement shuffle logic
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
	
}
