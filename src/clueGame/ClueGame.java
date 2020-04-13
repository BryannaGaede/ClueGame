package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame{

	private static Board board;
	private GUINotes notes;
	
	public ClueGame() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setCardConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		
		
		setSize(1200,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GUI Example");
		board = new Board();
		add(board, BorderLayout.CENTER);
		
		GUIControl gui = new GUIControl();
		add(gui.controlLabel(), BorderLayout.NORTH);// Now let's view it
		add(gui.controlButton(), BorderLayout.SOUTH);
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(createFileMenu());
		add(menu, BorderLayout.EAST);
		//add(gui);
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createShowNotesItem());
		return menu;
		
	}
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createShowNotesItem() {
		JMenuItem item = new JMenuItem("ShowNotes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				notes = new GUINotes();
				notes.setVisible(true);
				
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setTitle("Clue Game");
		frame.setVisible(true);
	}
	
}
