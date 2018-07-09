package com.vdp.rest.wss.api;

public class CompuWssRequest {

	private String		id;
	private String		login;
	private String		passwordToken;
	private CompuDoc	document;

	public String getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public CompuDoc getDocument() {
		return document;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	public void setDocument(CompuDoc document) {
		this.document = document;
	}

}
