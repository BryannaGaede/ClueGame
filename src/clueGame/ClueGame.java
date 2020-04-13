package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

	private static Board board;
	
	public ClueGame() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		setSize(1200,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GUI Example");
		board = new Board();
		add(board, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setTitle("Clue Game");
		frame.setVisible(true);
	}
	
}
