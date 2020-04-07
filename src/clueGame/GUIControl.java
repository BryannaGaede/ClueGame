package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
/*
public class GUI_Example extends JPanel {
	private JTextField name;

	public GUI_Example()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createNamePanel();
		add(panel);
		panel = createButtonPanel();
		add(panel);
	}

	 private JPanel createNamePanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel nameLabel = new JLabel("Name");
			name = new JTextField(20);
			panel.add(nameLabel);
			panel.add(name);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Who are you?"));
			return panel;
	}
	 
	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JButton agree = new JButton("I agree");
		JButton disagree = new JButton("I disagree");
		JPanel panel = new JPanel();
		panel.add(agree);
		panel.add(disagree);
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Example");
		frame.setSize(250, 150);	
		// Create the JPanel and add it to the JFrame
		GUI_Example gui = new GUI_Example();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}


}*/



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIControl extends JPanel {
	private JTextField turn, guess, guessResult, roll;
	private JButton next, accuse;
	
	public JPanel controlLabel() {
		//making text field "Who's turn"
		turn = new JTextField();
		turn.setBorder(BorderFactory.createTitledBorder("Who's turn?"));
		//new text field "Guess"
		guess = new JTextField();
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		//new Text field "guess result"
		guessResult = new JTextField();
		guessResult.setBorder(BorderFactory.createTitledBorder("Guess Result"));
		//new text field "roll"
		roll = new JTextField();
		roll.setBorder(BorderFactory.createTitledBorder("Roll"));
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
		accuse.setText("Make an accusation");
		secondRow.add(next);
		secondRow.add(accuse);
		add(secondRow);
		return secondRow;
	}
	
	public static void main(String[] args) {
		//build the jframe
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Example");
		frame.setSize(500, 100);
		//add the jpanels to the jframe
		GUIControl gui = new GUIControl();
		frame.add(gui.controlLabel(), BorderLayout.NORTH);// Now let's view it
		frame.add(gui.controlButton(), BorderLayout.SOUTH);
		frame.add(gui);
		frame.setVisible(true);
	}
	
	
	
	
}
