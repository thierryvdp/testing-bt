package irl.client.interfaces;

public interface ILoginGui {

	public String getLogin();
	public String getPassword();
	public void setLogin(String _login);
	public void setPassword(String _password);
	public void enableLoginButton();
	public void disableLoginButton();
	public void setMessageLabel(String _message);
	public void openGui();
	public void closeGui();
	public void setLoginAction(IAction _action);
	
}
