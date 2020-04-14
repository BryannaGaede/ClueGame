
package clueGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class GUIHumanCards extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel controlLabel(ArrayList<Card> playerCards) {
	
		JPanel suspectCards = new JPanel();
		suspectCards.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Suspects:"));
		suspectCards.setLayout(new BoxLayout(suspectCards, BoxLayout.Y_AXIS));
		JPanel weaponCards = new JPanel();
		weaponCards.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapons:"));
		weaponCards.setLayout(new BoxLayout(weaponCards, BoxLayout.Y_AXIS));
		JPanel roomCards = new JPanel();
		roomCards.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Rooms"));
		roomCards.setLayout(new BoxLayout(roomCards, BoxLayout.Y_AXIS));
		JPanel blankSpace = new JPanel();
		blankSpace.setLayout(new BoxLayout(blankSpace, BoxLayout.Y_AXIS));
		
		int numCards = 0;
		int[] typeConfirm = {0,0,0};
		
		for(Card card : playerCards) {
			switch(card.getType()) {
			case WEAPON:
				weaponCards.add(makeCard(card.getName(),true));
				numCards += 1;
				typeConfirm[0] += 1;
				break;
			case ROOM:
				roomCards.add(makeCard(card.getName(),true));
				numCards += 1;
				typeConfirm[1] += 1;
				break;
			case PERSON:
				suspectCards.add(makeCard(card.getName(),true));
				numCards += 1;
				typeConfirm[2] += 1;
				break;
			default:
				break;
			}
		}
		while(numCards < 7) {
			blankSpace.add(new JPanel());
			numCards += 1;
		}
		if(typeConfirm[0] == 0) {
			weaponCards.add(new JPanel());
		} else if(typeConfirm[1] == 0) {
			roomCards.add(new JPanel());
		} else if(typeConfirm[2] == 0) {
			suspectCards.add(new JPanel());
		}
		
		//adding in the different text fields to "first row" upper
		JPanel allPlayerCards = new JPanel();
		allPlayerCards.setBorder(BorderFactory.createLineBorder(Color.black));
		allPlayerCards.add(new JLabel("YOUR CARDS"));
		allPlayerCards.setLayout(new BoxLayout(allPlayerCards, BoxLayout.Y_AXIS));
		allPlayerCards.add(suspectCards);
		allPlayerCards.add(roomCards);
		allPlayerCards.add(weaponCards);
		allPlayerCards.add(blankSpace);
		add(allPlayerCards);
		return allPlayerCards;	
	}
	
	public JPanel makeCard(String name, boolean isCard) {
		JPanel card = new JPanel();
		card.setBorder(BorderFactory.createLineBorder(Color.black));
		card.add(new JLabel(name));
		if(isCard) {
			card.setBackground(Color.white);
		}
		return card;
	}
		
}
