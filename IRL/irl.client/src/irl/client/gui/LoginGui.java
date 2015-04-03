package irl.client.gui;

import irl.client.interfaces.IAction;
import irl.client.interfaces.ILoginGui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginGui extends JFrame implements ActionListener, ILoginGui {

	private static final long serialVersionUID = 1L;

	private JPanel loginPanel;
	private JButton btnLogin;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JLabel lblMessage;
	private IAction action;

	// Constructor
	public LoginGui() {

		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(3, 2));

		// Add the widgets.
		addWidgets();

		// Add the panel to the frame.
		getContentPane().add(loginPanel, BorderLayout.CENTER);

		// Exit when the window is closed.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Show the converter.
	}

	// Create and add the widgets for login pannel
	private void addWidgets() {

		lblEmail = new JLabel("Login ", SwingConstants.LEFT);
		txtEmail = new JTextField();
		lblPassword = new JLabel("Password ", SwingConstants.LEFT);
		txtPassword = new JPasswordField();
		txtPassword.setEchoChar('*');
		btnLogin = new JButton("Login");
		lblMessage = new JLabel("<-------->");

		// Listen to events from Convert button.
		btnLogin.addActionListener(this);

		// Add widgets to container.
		loginPanel.add(lblEmail);
		loginPanel.add(txtEmail);
		loginPanel.add(lblPassword);
		loginPanel.add(txtPassword);
		loginPanel.add(btnLogin);
		loginPanel.add(lblMessage);

		setVisible(true);
		action=null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(action!=null)
			action.run();
	}

	@Override
	public void closeGui() {
		dispose();
	}

	@Override
	public void disableLoginButton() {
		btnLogin.setEnabled(false);
	}

	@Override
	public void enableLoginButton() {
		btnLogin.setEnabled(true);
	}

	@Override
	public String getLogin() {
		return txtEmail.getText();
	}

	@Override
	public String getPassword() {
		return txtPassword.getPassword().toString();
	}

	@Override
	public void openGui() {
		pack();
		setVisible(true);
	}

	@Override
	public void setLogin(String _login) {
		txtEmail.setText(_login);
	}

	@Override
	public void setMessageLabel(String _message) {
		lblMessage.setText(_message);
	}

	@Override
	public void setPassword(String _password) {
		txtPassword.setText(_password);
	}

	@Override
	public void setLoginAction(IAction _action) {
		action=_action;
	}

}