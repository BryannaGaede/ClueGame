
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
		//make text fields
		people= new JTextField();
		people.setBorder(BorderFactory.createTitledBorder("People Card"));
		people.setText(playerCards.get(0).getName());
		people.setEditable(false);
		people.setColumns(20);
		room = new JTextField();
		room.setBorder(BorderFactory.createTitledBorder("Room Card"));
		room.setEditable(false);
		room.setColumns(20);
		room.setText(playerCards.get(1).getName());
		weapon = new JTextField();
		weapon.setBorder(BorderFactory.createTitledBorder("Weapon Card"));
		weapon.setEditable(false);
		weapon.setColumns(20);
		weapon.setText(playerCards.get(2).getName());
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
