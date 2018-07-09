package com.vdp.rest.wss.api;

import java.util.List;

public class CompuDoc {

	private String					id;
	private List<CompuFieldValue>	fields;
	private List<CompuDoc>			documents;

	public String getId() {
		return id;
	}

	public List<CompuFieldValue> getFields() {
		return fields;
	}

	public List<CompuDoc> getDocuments() {
		return documents;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFields(List<CompuFieldValue> fields) {
		this.fields = fields;
	}

	public void setDocuments(List<CompuDoc> documents) {
		this.documents = documents;
	}

}
