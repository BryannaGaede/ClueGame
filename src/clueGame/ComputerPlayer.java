package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, int row2, int col, String color) {
		super(name, row2, col, color);
		super.status = Status.COMPUTER;
	}
	public BoardCell pickLocaation(Set<BoardCell> targets) {
		return null;
	}
	public void makeAccusation() {
		
	}
	public void createSuggestion() {
		
	}
}
