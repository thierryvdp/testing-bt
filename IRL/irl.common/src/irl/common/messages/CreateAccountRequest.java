package irl.common.messages;


public class CreateAccountRequest extends AbstractRequest {
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;

	public CreateAccountRequest(String _email,String _password) {
		email=_email;
		password = _password;
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

}
