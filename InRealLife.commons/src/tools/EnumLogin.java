package tools;

public enum EnumLogin {
	
	COMMAND(0),EMAIL(1),PASSWORD(2),SIZE(3);
	
	protected int field;
	
	EnumLogin(int _field) {
		this.field=_field;
	}
	
	public int getMessageColumn() {
		return this.field;
	}
	
	public EnumLogin getLogin(String fieldName) {
		if(fieldName.equals("EMAIL")) return EnumLogin.EMAIL;
		if(fieldName.equals("PASSWORD")) return EnumLogin.PASSWORD;
		return EnumLogin.COMMAND;
	}
	
}
