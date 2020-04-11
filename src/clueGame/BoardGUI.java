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
	
	@Override 
	public void paintComponent(Graphics g)  {
		super.paintComponent(g);
		
		//drawing for testing
				g.setColor(Color.BLUE);
				g.drawRect(50, 50, 5, 5);
				
		//initializing the board
		/*
		 * Board board1;
		 
		board1 = Board.getInstance();
		board1.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		board1.initialize();
		//drawing for testing
		g.setColor(Color.BLUE);
		g.drawRect(50, 50, 5, 5);
		
		BoardCell board[][] = new BoardCell[30][30];
		board = board1.getBoard();
	
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; i < board[i].length; j++) {
				board[i][j].draw(g, 2);
			}
		}*/
		
	}
	

}