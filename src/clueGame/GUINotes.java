package clueGame;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTextField;

public class GUINotes extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField myName;
	private ArrayList<Player> allPlayers = Board.getPlayers();
	private ArrayList<Room> allRooms = Board.getRooms();
	private ArrayList<Weapon> allWeapons = Board.getWeapons();
	public GUINotes() {
		
		setTitle("Detective Notes");
		setSize(300, 3200);
		myName = new JTextField(20);
		add(myName);
		
		
	}
}
