package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardGUI extends JPanel {
	
	public BoardGUI() {
	}
	
	private static Board board;
	
	@Override 
	public void paintComponent(Graphics g)  {
		super.paintComponent(g);
		
		//drawing for testing
		g.setColor(Color.BLUE);
		g.drawRect(50, 50, 5, 5);
				
		//initializing the board
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		
		BoardCell[][] board1 = new BoardCell[30][30];
		board1 = board.getBoard();
		
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; i < board1[i].length; j++) {
				board1[i][j].draw(g, 2);
			}
		}
		
	}
	

}