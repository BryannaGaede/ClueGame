package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Board board;
	private GUINotes notes;
	private static ArrayList<Player> players = Board.getPlayers();
	private static int playerIndex = Board.getPlayerIndex();
	
	public ClueGame() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setCardConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		
//		for (Player x: board.getPlayers()) {
//			if (x.status == Status.HUMAN) {
//				human = x;
//			}
//		}
//		
		setSize(800,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		board = new Board();
		add(board, BorderLayout.CENTER);
		
		GUIControl gui = new GUIControl();
		GUIHumanCards gui2 = new GUIHumanCards();
		JPanel inGameOptions = new JPanel();
		inGameOptions.setLayout(new GridLayout(1,2));
		inGameOptions.add(gui.controlLabel(),0,0);
		inGameOptions.add(gui.controlButton(),1,0);
		add(gui2.controlLabel(players.get(playerIndex).getMyCards()), BorderLayout.EAST);
		add(inGameOptions, BorderLayout.SOUTH);
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(createFileMenu());
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
		if(players.get(playerIndex).getStatus() == Status.HUMAN) {
			JOptionPane.showMessageDialog(board, "You are the "+ players.get(playerIndex).getName() +" . Press Next Player to begin play", "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
