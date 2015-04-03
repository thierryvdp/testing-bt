package tools;

public enum EnumSeparator {
	
	RECORD("<CR>"), FIELD_VALUE("<=>"), FIELD("<;>"), VALUES("<+>"), UNKNOWN("UNKNOWN");
	
	protected String separator;
	
	EnumSeparator(String _separator) {
		this.separator=_separator;
	}
	
	public String getSeparatorString() {
		return this.separator;
	}
	
	public EnumSeparator getSeparator(String _separator) {
		if(_separator.equals("<CR>")) return EnumSeparator.RECORD;
		if(_separator.equals("<=>")) return EnumSeparator.FIELD_VALUE;
		if(_separator.equals("<;>")) return EnumSeparator.FIELD;
		if(_separator.equals("<+>")) return EnumSeparator.VALUES;
		return EnumSeparator.UNKNOWN;
	}

}
