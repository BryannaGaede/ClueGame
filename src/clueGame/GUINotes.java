package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
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
		setSize(1000, 800);
		myName = new JTextField(20);
		add(myName);
		
		JPanel playerNotes = new JPanel();
		playerNotes.setLayout(new GridLayout(3,3));
		
		JPanel roomChecks = new JPanel();
		for(Room room : allRooms) {
			JCheckBox option = new JCheckBox(room.getName());
			roomChecks.add(option);
		}
		roomChecks.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Rooms:"));
		playerNotes.add(roomChecks);

		JPanel suspectChecks = new JPanel();
		for(Player player : allPlayers) {
			JCheckBox option = new JCheckBox(player.getName());
			suspectChecks.add(option);
		}
		suspectChecks.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Suspects:"));
		playerNotes.add(suspectChecks);
		
		JPanel weaponChecks = new JPanel();
		for(Weapon weapon : allWeapons) {
			JCheckBox option = new JCheckBox(weapon.getName());
			weaponChecks.add(option);
		}
		weaponChecks.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapons:"));
		playerNotes.add(weaponChecks);
		
		JPanel userRoomChoice = new JPanel();
		userRoomChoice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Player Room Choice:"));
		JComboBox availableRooms = new JComboBox();
		availableRooms.addItem("UNSURE");
		for(Room room : allRooms) {
			availableRooms.addItem(room.getName());
		}
		userRoomChoice.add(availableRooms);
		playerNotes.add(userRoomChoice);
		
		
		JPanel userSuspectChoice = new JPanel();
		userSuspectChoice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Player Suspect Choice:"));
		JComboBox availableSuspects = new JComboBox();
		availableSuspects.addItem("UNSURE");
		for(Player suspect : allPlayers) {
			availableSuspects.addItem(suspect.getName());
		}
		userSuspectChoice.add(availableSuspects);
		playerNotes.add(userSuspectChoice);
		
		JPanel userWeaponChoice = new JPanel();
		userWeaponChoice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Player Weapon Choice:"));
		JComboBox availableWeapons = new JComboBox();
		availableWeapons.addItem("UNSURE");
		for(Weapon weapon : allWeapons) {
			availableWeapons.addItem(weapon.getName());
		}
		userWeaponChoice.add(availableWeapons);
		playerNotes.add(userWeaponChoice);
		

		add(playerNotes);
	}
}
