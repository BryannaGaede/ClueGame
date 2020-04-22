package clueGame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	public JComboBox<String> availableSuspects = new JComboBox<String>();
	public JComboBox<String> availableWeapons = new JComboBox<String>();
	public JComboBox<String> availableRooms = new JComboBox<String>();
	
	public AccusationHandler() {
		setTitle("Make Accusation");
		setSize(500, 300);
		
		JPanel suggestionOptions = new JPanel();
		suggestionOptions.setLayout(new GridLayout(3,2));
		
		JPanel rooms = new JPanel();
		
		for(Room room : allRooms) {
			availableRooms.addItem(room.getName());
		}
		rooms.add(availableRooms);
		
		JPanel suspects = new JPanel();
		suspects.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Suspect:"));
		
		for(Player suspect : allPlayers) {
			availableSuspects.addItem(suspect.getName());
		}
		suspects.add(availableSuspects);
		JPanel weapons = new JPanel();
		
		weapons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapon:"));
		
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
		submit.addActionListener(new makeAccusation());
		
		suggestionOptions.add(submit,0,2);
		
		add(suggestionOptions);
	}
	
	public class makeAccusation implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			{	
				Solution suggest = new Solution(String.valueOf(availableSuspects.getSelectedItem()), String.valueOf(availableRooms.getSelectedItem()), String.valueOf(availableWeapons.getSelectedItem()));
				//returns card type
				boolean won = Board.checkAccusation(suggest);
				if (won) {
					JOptionPane.showMessageDialog(Board.getInstance(), "You are the winner!", "Accusation", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
					JOptionPane.showMessageDialog(Board.getInstance(), "You did not guess correctly", "Accusation", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		}
	}
}
