package clueGame;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class GUIControl extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField turn, guess, guessResult, roll;
	private JButton next, accuse;
	Dimension buttonSize = new Dimension(150,50);
	Font buttonFont = new Font("Arial", Font.BOLD, 12);
	
	public JPanel controlLabel() {
		//make text fields
		turn = new JTextField();
		turn.setBorder(BorderFactory.createTitledBorder("Who's turn?"));
		turn.setEditable(false);
		guess = new JTextField();
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		guess.setEditable(false);
		guessResult = new JTextField();
		guessResult.setBorder(BorderFactory.createTitledBorder("Guess Result"));
		guessResult.setEditable(false);
		roll = new JTextField();
		roll.setBorder(BorderFactory.createTitledBorder("Roll"));
		roll.setEditable(false);
		//adding in the different text fields to "first row" upper
		JPanel firstRow = new JPanel();
		firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));
		firstRow.add(roll);
		firstRow.add(turn);
		firstRow.add(guess);
		firstRow.add(guessResult);
		add(firstRow);
		return firstRow;
	}
	
	public JPanel controlButton() {
		//adding buttons too "second row" (lower)
		JPanel secondRow = new JPanel();
		next = new JButton();
		accuse = new JButton();
		next.setText("Next Player");
		accuse.setText("Make Accusation");
		next.setPreferredSize(buttonSize);
		accuse.setPreferredSize(buttonSize);
		next.setFont(buttonFont);
		accuse.setFont(buttonFont);
		secondRow.add(next);
		secondRow.add(accuse);
		add(secondRow);
		next.addActionListener(new NextPlayerClicked());
		accuse.addActionListener(new AccusationRequested());
		return secondRow;
	}
	
	private class NextPlayerClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			{
				Board.handleNextPlayer();
				repaint();
			}
		}
	}
	private class AccusationRequested implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			{
				Board.handleAccusationRequestFromHumanPlayer();
				repaint();
			}
		}
	}		
}
