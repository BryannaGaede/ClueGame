package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

	BoardGUI boardGUI1;
	private static Board board;
	
	public ClueGame() {
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardGUI1 = new BoardGUI();
		add(boardGUI1, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		
		//initializing the board
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	
		ClueGame frame = new ClueGame();
		frame.setVisible(true);
	}
	
}
