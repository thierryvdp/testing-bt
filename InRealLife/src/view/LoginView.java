package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import action.LoginAction;

public class LoginView extends ViewPart {

	public static final String ID = "InRealLife.view.LoginView";
	private Text txtEmailLogin;
	private Text txtPassword;
	private Button butPassword;
	private Text txtTest;
	private LoginAction loginAction;
	
	public void createPartControl(Composite _parent) {
		
		/**
		 * Parent
		 */
		FormLayout flParent = new FormLayout();
		flParent.marginHeight = 0;
		flParent.marginWidth = 0;
		flParent.spacing = 0;
		_parent.setLayout(flParent);
		
		/** ******************************************************************************************
		 * composite cLogin
		 */
		Composite cLogin = new Composite(_parent, SWT.NONE);
		FormLayout flLogin = new FormLayout();
		flLogin.marginHeight = 5;
		flLogin.marginWidth = 5;
		flLogin.spacing = 5;
		cLogin.setLayout(flLogin);
		final FormData fdLogin = new FormData();
		fdLogin.left = new FormAttachment(0,0);
		fdLogin.right = new FormAttachment(100,0);
		cLogin.setLayoutData(fdLogin);
		
		/**
		 * EmailLogin
		 */
		
		txtEmailLogin = new Text(cLogin, SWT.BORDER);
		txtEmailLogin.setTextLimit(50);
		final FormData fdTxtEmailLogin = new FormData();
		fdTxtEmailLogin.left = new FormAttachment(0, 100);
		fdTxtEmailLogin.right = new FormAttachment(100, 0);
		txtEmailLogin.setLayoutData(fdTxtEmailLogin);

		final Label lblEmailLogin = new Label(cLogin, SWT.NONE);
		lblEmailLogin.setText("Email");
		final FormData fdLblEmailLogin = new FormData();
		fdLblEmailLogin.top = new FormAttachment(txtEmailLogin, 0, SWT.CENTER);
		fdLblEmailLogin.right = new FormAttachment(txtEmailLogin, 0, SWT.LEFT);
		lblEmailLogin.setLayoutData(fdLblEmailLogin);
		
		/**
		 * Password
		 */
		txtPassword = new Text(cLogin, SWT.BORDER);
		txtPassword.setTextLimit(50);
		txtPassword.setEchoChar('*');
		final FormData fdTxtPassword = new FormData();
		fdTxtPassword.left = new FormAttachment(txtEmailLogin,0, SWT.LEFT);
		fdTxtPassword.right = new FormAttachment(100, 0);
		fdTxtPassword.top = new  FormAttachment(txtEmailLogin,0, SWT.BOTTOM);
		txtPassword.setLayoutData(fdTxtPassword);

		final Label lblPassword = new Label(cLogin, SWT.NONE);
		lblPassword.setText("Password");
		final FormData fdLblPassword = new FormData();
		fdLblPassword.top = new FormAttachment(txtPassword, 0, SWT.CENTER);
		fdLblPassword.right = new FormAttachment(txtPassword, 0, SWT.LEFT);
		lblPassword.setLayoutData(fdLblPassword);

		/**
		 * Bouton Login
		 */
		butPassword = new Button(cLogin, SWT.NONE);
		butPassword.setText("Connect");
		final FormData fdButPassword = new FormData();
		fdButPassword.left = new FormAttachment(txtPassword,0, SWT.LEFT);
		fdButPassword.right = new FormAttachment(100, 0);
		fdButPassword.top = new  FormAttachment(txtPassword,0, SWT.BOTTOM);
		butPassword.setLayoutData(fdButPassword);
		loginAction= new LoginAction(this);
		butPassword.addSelectionListener(new SelectionListener() {		
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginAction.run();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		

		/** ******************************************************************************************
		 * composite cLogued
		 */
		Composite cLogued = new Composite(_parent, SWT.NONE);
		FormLayout flCLogued = new FormLayout();
		flCLogued.marginHeight = 5;
		flCLogued.marginWidth = 5;
		flCLogued.spacing = 5;
		cLogued.setLayout(flCLogued);
		final FormData fdCLogued = new FormData();
		fdCLogued.left = new FormAttachment(cLogin,0,SWT.LEFT);
		fdCLogued.right = new FormAttachment(cLogin,0,SWT.RIGHT);
		fdCLogued.top = new FormAttachment(cLogin,0,SWT.BOTTOM);
		cLogued.setLayoutData(fdCLogued);
		
		/**
		 * test
		 */
		txtTest = new Text(cLogued, SWT.BORDER);
		txtTest.setTextLimit(50);
		final FormData fdTxtTest = new FormData();
		fdTxtTest.left = new FormAttachment(0, 100);
		fdTxtTest.right = new FormAttachment(100, 0);
		txtTest.setLayoutData(fdTxtTest);

		final Label lblTest = new Label(cLogued, SWT.NONE);
		lblTest.setText("Test");
		final FormData fdLblTest = new FormData();
		fdLblTest.top = new FormAttachment(txtTest, 0, SWT.CENTER);
		fdLblTest.right = new FormAttachment(txtTest, 0, SWT.LEFT);
		lblTest.setLayoutData(fdLblTest);
		
	}

	public void setFocus() {
	}

	public Text getTxtEmailLogin() {
		return txtEmailLogin;
	}

	public void setTxtEmailLogin(Text txtEmailLogin) {
		this.txtEmailLogin = txtEmailLogin;
	}

	public Text getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(Text txtPassword) {
		this.txtPassword = txtPassword;
	}
}
