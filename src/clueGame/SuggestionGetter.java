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
import javax.swing.JPanel;

public class SuggestionGetter extends JDialog {
	Dimension buttonSize = new Dimension(150,50);
	Font buttonFont = new Font("Arial", Font.BOLD, 12);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Player> allPlayers = Board.getPlayers();
	private ArrayList<Room> allRooms = Board.getRooms();
	private ArrayList<Weapon> allWeapons = Board.getWeapons();
	public JComboBox<String> availableSuspects = new JComboBox<String>();
	public JComboBox<String> availableWeapons = new JComboBox<String>();
	public SuggestionGetter() {
		setTitle("Make Suggestion");
		setSize(500, 300);
		
		JPanel suggestionOptions = new JPanel();
		suggestionOptions.setLayout(new GridLayout(3,2));
		
		JPanel room = new JPanel();
		JLabel roomName = new JLabel((Board.getPlayerRoom()));
		room.add(roomName);
		room.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Room:"));
		JPanel suspects = new JPanel();
		
		suspects.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Suspect:"));
		//JComboBox<String> availableSuspects = new JComboBox<String>();
		for(Player suspect : allPlayers) {
			availableSuspects.addItem(suspect.getName());
		}
		suspects.add(availableSuspects);
		JPanel weapons = new JPanel();
		
		weapons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapon:"));
		//JComboBox<String> availableWeapons = new JComboBox<String>();
		for(Weapon weapon : allWeapons) {
			availableWeapons.addItem(weapon.getName());
		}
		weapons.add(availableWeapons);
		
		suggestionOptions.add(room);
		suggestionOptions.add(suspects);
		suggestionOptions.add(weapons);
		
		JButton submit = new JButton();
		submit.setText("Make Suggestion");
		submit.setPreferredSize(buttonSize);
		submit.setFont(buttonFont);
		submit.addActionListener(new makeSuggestion());
		suggestionOptions.add(submit,0,2);
		
		add(suggestionOptions);
		
		
	}
	
	public class makeSuggestion implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			{		
				Solution suggest = new Solution(String.valueOf(availableSuspects.getSelectedItem()), Board.getPlayers().get(Board.getPlayerIndex()).getRoom(), String.valueOf(availableWeapons.getSelectedItem()));
				//returns card type
				Board.handleSuggestion(suggest, Board.getPlayers().get(Board.getPlayerIndex()));
			}
		}
	}

	
}
