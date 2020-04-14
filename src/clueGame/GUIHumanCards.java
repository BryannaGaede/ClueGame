
package clueGame;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class GUIHumanCards extends JPanel {
	/**
	 * 
	 */
	private JTextField people, room, weapon;
	private JButton next, accuse;
	Dimension buttonSize = new Dimension(200,100);
	Font buttonFont = new Font("Arial", Font.BOLD, 15);
	
	public JPanel controlLabel(ArrayList<Card> playerCards) {
		Card personCard = null;
		Card roomCard = null; 
		Card weaponCard = null;
		for (Card x : playerCards) {
			if (x.type == CardType.PERSON) {
				personCard = x;
			}
			else if (x.type == CardType.ROOM) {
				roomCard = x;
			}
			else if (x.type == CardType.WEAPON) {
				weaponCard = x;
			}
		}
		//make text fields
		people= new JTextField();
		people.setBorder(BorderFactory.createTitledBorder("People Card"));
		if (personCard.getName() != null) {
			people.setText(personCard.getName());
		}
		people.setEditable(false);
		people.setColumns(20);
		room = new JTextField();
		room.setBorder(BorderFactory.createTitledBorder("Room Card"));
		room.setEditable(false);
		room.setColumns(20);
		if (roomCard.getName() != null) {
			room.setText(roomCard.getName());
		}
		weapon = new JTextField();
		weapon.setBorder(BorderFactory.createTitledBorder("Weapon Card"));
		weapon.setEditable(false);
		weapon.setColumns(20);
		if (weaponCard.getName() != null) {
			weapon.setText(weaponCard.getName());
		}
		//adding in the different text fields to "first row" upper
		JPanel Column = new JPanel();
		Column.setLayout(new BoxLayout(Column, BoxLayout.Y_AXIS));
		Column.add(people);
		Column.add(room);
		Column.add(weapon);
		add(Column);
		return Column;
	}
		
}
