package lucie.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanelGui extends Frame implements ActionListener {
	
	private Label label;
	private Button okButton;
	private boolean click=true;
	
	public ControlPanelGui () { /* The constructor is responsible for setting
		* up the initial buttons and colour background. */ 
		setLayout(new FlowLayout(FlowLayout.CENTER)); 
		setBackground(Color.black); setForeground(Color.cyan);
		
		// create and add a Label
		label = new Label("C L I C K"); 
		add(label);

		// create & add a button, plus an ActionListener to the button
		okButton = new Button("Ok"); 
		okButton.addActionListener(this); 
		add(okButton);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(click) {
			label.setText("C L A C K");
		}
		else {
			label.setText("C L I C K");
		}
		click=!click;

	}

}
