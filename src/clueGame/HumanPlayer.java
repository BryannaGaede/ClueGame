package clueGame;

public class HumanPlayer extends Player {
	public HumanPlayer(String name, int row2, int col, String color) {
		super(name, row2, col, color);
		super.status = Status.HUMAN;
	}
}
