package clueGame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccusationHandler extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension buttonSize = new Dimension(150,50);
	Font buttonFont = new Font("Arial", Font.BOLD, 12);
	
	private ArrayList<Player> allPlayers = Board.getPlayers();
	private ArrayList<Room> allRooms = Board.getRooms();
	private ArrayList<Weapon> allWeapons = Board.getWeapons();
	
	public AccusationHandler() {
		setTitle("Make Accusation");
		setSize(500, 300);
		
		JPanel suggestionOptions = new JPanel();
		suggestionOptions.setLayout(new GridLayout(3,2));
		
		JPanel rooms = new JPanel();
		JComboBox<String> availableRooms = new JComboBox<String>();
		for(Room room : allRooms) {
			availableRooms.addItem(room.getName());
		}
		rooms.add(availableRooms);
		
		JPanel suspects = new JPanel();
		suspects.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Suspect:"));
		JComboBox<String> availableSuspects = new JComboBox<String>();
		for(Player suspect : allPlayers) {
			availableSuspects.addItem(suspect.getName());
		}
		suspects.add(availableSuspects);
		JPanel weapons = new JPanel();
		
		weapons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapon:"));
		JComboBox<String> availableWeapons = new JComboBox<String>();
		for(Weapon weapon : allWeapons) {
			availableWeapons.addItem(weapon.getName());
		}
		weapons.add(availableWeapons);
		
		suggestionOptions.add(rooms);
		suggestionOptions.add(suspects);
		suggestionOptions.add(weapons);
		
		JButton submit = new JButton();
		submit.setText("Make Accusation");
		submit.setPreferredSize(buttonSize);
		submit.setFont(buttonFont);
		
		suggestionOptions.add(submit,0,2);
		
		add(suggestionOptions);
	}
}
