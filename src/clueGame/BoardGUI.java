package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardGUI extends JFrame {
	public Board board;
	public void setUp () {
	Board board;
	// Board is singleton, get the only instance
	board = Board.getInstance();
	// set the file names to use my config files
	board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
	// Initialize will load BOTH config files 
	board.initialize();
	}
	
	public BoardGUI() {
	super();	
	setUp();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Clue Game");
	
	setSize(680,680);

	
	JPanel rooms = new JPanel();

		
	rooms.setLayout(new BoxLayout(rooms, BoxLayout.Y_AXIS));
	
	
	rooms.setBorder(BorderFactory.createTitledBorder("Rooms"));
	Graphic g;
	g.setFont();
	
	
	}

	
	public static void main(String[] args) throws IOException {
		
		BoardGUI gui = new BoardGUI();
		gui.setVisible(true);
	}
	
}