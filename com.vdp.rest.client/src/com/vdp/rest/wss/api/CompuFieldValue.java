package com.vdp.rest.wss.api;

public class CompuFieldValue {

	private String			id;
	private CompuFieldType	fieldType;
	private String			fieldStringValue;

	public String getId() {
		return id;
	}

	public CompuFieldType getFieldType() {
		return fieldType;
	}

	public String getFieldStringValue() {
		return fieldStringValue;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFieldType(CompuFieldType fieldType) {
		this.fieldType = fieldType;
	}

	public void setFieldStringValue(String fieldStringValue) {
		this.fieldStringValue = fieldStringValue;
	}

}
