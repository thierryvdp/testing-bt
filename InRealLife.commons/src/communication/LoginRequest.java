package communication;

import java.io.Serializable;

public class LoginRequest implements Serializable {
	
	private String email;
	private String password;
	private boolean acceptedConnection;
	
	public LoginRequest(String _email,String _password) {
		email=_email;
		password=_password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAcceptedConnection() {
		return acceptedConnection;
	}
	public void setAcceptedConnection(boolean acceptedConnection) {
		this.acceptedConnection = acceptedConnection;
	}
	
	

}
