package clueGame;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		setSize(800,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		addMouseListener(new ChangeLocation());
		add(board, BorderLayout.CENTER);
		GUIControl gui = new GUIControl();
		GUIHumanCards gui2 = new GUIHumanCards();
		add(gui2.controlLabel(players.get(playerIndex).getMyCards()), BorderLayout.EAST);
		add(gui, BorderLayout.SOUTH);
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
		JOptionPane.showMessageDialog(board, "You are the "+ players.get(1).getName() +" . Press Next Player to begin play", "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private class ChangeLocation implements MouseListener {
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			boolean window = Board.changeLocation(e);
			if (window == false) {
				JOptionPane.showMessageDialog(board, "Wrong", "Invalid Click!", JOptionPane.INFORMATION_MESSAGE);
			}
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
