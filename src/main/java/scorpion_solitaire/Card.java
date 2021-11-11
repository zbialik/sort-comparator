package scorpion_solitaire;

public class Card {
	
	int value; // note: ace=1, jack=11, queen=12, king=13
	char suit; // 'D', 'H', 'C', or 'S'
	boolean faceUp;

	public Card(int val, char s, boolean up) {
		this.value = val;
		this.suit = s;
		this.faceUp = up;
	}

}
