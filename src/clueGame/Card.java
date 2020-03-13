package clueGame;

public class Card {
	private CardType type;
	private String name;
	
	public Card(CardType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public boolean equals() {
		return true;
	}

	//used for testing
	public CardType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
