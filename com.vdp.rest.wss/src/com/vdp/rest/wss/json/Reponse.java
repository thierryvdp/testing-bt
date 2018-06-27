package com.vdp.rest.wss.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reponse {

	private int		id;
	private String	reponse;

	public int getId() {
		return id;
	}

	public String getReponse() {
		return reponse;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

}
