package clueGame;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
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
	
GUIControl() {
	setLayout(new GridLayout(1,2));
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
	turn.setText(Board.paintName);
	roll.setText(String.valueOf(Board.dieRoll));
	add(firstRow, 1, 0);
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
	add(secondRow,0,0);
}

	private class NextPlayerClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			{	
				//Board.turnOver = false;
				Board.handleNextPlayer();
					turn.setText(Board.paintName);
					roll.setText(String.valueOf(Board.dieRoll));
			}
		}
	}
	
	private class AccusationRequested implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			{	

				AccusationHandler accusation = new AccusationHandler();
				accusation.setVisible(true);
				JOptionPane.showMessageDialog(accusation, "Make an Auggestion");
				Board.handleAccusationRequestFromHumanPlayer();
				repaint();
			}
		}
	}	
	
	public JButton getNext() {
		return next;
	}

	public JButton getAccuse() {
		return accuse;
	}
	
	public JTextField getTurn() {
		return turn;
	}
	
	public void setNextText(String s) {
		turn.setText(s);
	}
	
	public void setRollText(int currentRoll){
		roll.setText(String.valueOf(currentRoll));
	}
	
	public void setGuessText(String s) {
		guess.setText(s);
	}
	
	public void setGuessResultText(String s){
		guessResult.setText(s);
	}
}
