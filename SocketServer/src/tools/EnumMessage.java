package tools;

public enum EnumMessage {
	
	LOGIN("LOGIN"), UNKNOWN("UNKNOWN");
	
	protected String separator;
	
	EnumMessage(String _separator) {
		this.separator=_separator;
	}
	
	public String getMessageString() {
		return this.separator;
	}
	
	public EnumMessage getMessage(String _separator) {
		if(_separator.equals("LOGIN")) return EnumMessage.LOGIN;
		return EnumMessage.UNKNOWN;
	}

}
