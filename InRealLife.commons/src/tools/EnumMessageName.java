package tools;

public enum EnumMessageName {
	
	MESSAGE("MESSAGE"),LOGIN("LOGIN"),LOGRESPONSE("LOGRESPONSE"), UNKNOWN("UNKNOWN");
	
	protected String messageName;
	
	EnumMessageName(String _messageName) {
		this.messageName=_messageName;
	}
	
	public String getName() {
		return this.messageName;
	}
	
	public EnumMessageName getEnum(String _separator) {
		if(_separator.equals("MESSAGE")) return EnumMessageName.MESSAGE;
		if(_separator.equals("LOGIN")) return EnumMessageName.LOGIN;
		if(_separator.equals("LOGRESPONSE")) return EnumMessageName.LOGRESPONSE;
		return EnumMessageName.UNKNOWN;
	}

}
