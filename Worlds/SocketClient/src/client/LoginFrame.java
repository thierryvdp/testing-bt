package client;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends Frame implements ActionListener {

	private Button okButton;
	private Label theLabel;

	public LoginFrame() {
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBackground(Color.black);
		setForeground(Color.cyan);
		setSize(300, 200);
		// create and add a Label
		theLabel = new Label("W A R N I N G");
		add(theLabel);
		okButton = new Button("Ok");
		okButton.addActionListener(this);
		add(okButton);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		setVisible(false);
		dispose();
		System.exit (0);
	}
	
}
