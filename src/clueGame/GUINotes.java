package clueGame;

import javax.swing.JDialog;
import javax.swing.JTextField;

public class GUINotes extends JDialog {
	private JTextField myName;
	
	public GUINotes() {
		setTitle("Detective Notes");
		setSize(300, 3200);
		myName = new JTextField(20);
		add(myName);
	}
}
