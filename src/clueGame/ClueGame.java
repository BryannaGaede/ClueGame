package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

	BoardGUI boardGUI1;
	
	public ClueGame() {
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardGUI1 = new BoardGUI();
		add(boardGUI1, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setVisible(true);
	}
	
}
