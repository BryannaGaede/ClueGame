package clueGame;

public class Card {
	CardType type;
	private String name;
	private boolean dealt;
	
	public Card(CardType type, String name) {
		super();
		this.type = type;
		this.name = name;
		this.dealt = false;
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

	public boolean getStatus() {
		return dealt;
	}

	public boolean isDealt() {
		return dealt;
	}

	public void setStatus(boolean dealt2) {
		this.dealt = dealt2;
	}
}
