package scorpion_solitaire;

public class Card {
	
	int value; // note: ace=1, jack=11, queen=12, king=13
	char suit; // 'D', 'H', 'C', or 'S'
	boolean faceUp;
	
	public Card() {
		this.faceUp = false;
	}

	public Card(int val, char s, boolean up) {
		this.value = val;
		this.suit = s;
		this.faceUp = up;
	}
	
	public String toString() {
		String output = "";
		String value;
		
		if (this.faceUp) {
			if (this.value == 1) {
				value = "A";
			} else if (this.value == 11) {
				value = "J";
			} else if (this.value == 12) {
				value = "Q";
			} else if (this.value == 13) {
				value = "K";
			} else {
				value = String.valueOf(this.value);
			}
			
			output = value + this.suit;
		} else {
			output = "??";
		}
		
		return output;
	}
	
	/**
	 * provides a clone of this Card
	 */
	public Card clone() {
		return new Card(this.value, this.suit, this.faceUp);
	}

}
