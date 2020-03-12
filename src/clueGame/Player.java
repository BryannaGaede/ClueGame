package clueGame;

import java.awt.Color;

public class Player {
	protected String playerName;
	protected int row;
	protected int column;
	protected Color color;
	protected Card[] myCards;
	protected Card[] seenCards;
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
}
