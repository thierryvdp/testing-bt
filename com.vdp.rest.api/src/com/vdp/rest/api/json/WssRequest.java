package com.vdp.rest.api.json;

public class WssRequest {

	private String		methodName;
	private String		login;
	private String		passwordToken;
	private FieldValue	param;

	public String getMethodName() {
		return methodName;
	}

	public String getLogin() {
		return login;
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public FieldValue getParam() {
		return param;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	public void setParam(FieldValue param) {
		this.param = param;
	}

}
