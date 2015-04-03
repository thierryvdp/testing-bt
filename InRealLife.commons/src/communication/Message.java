package communication;

import java.util.HashMap;
import java.util.Set;

import tools.EnumSeparator;

public class Message {
	
	private HashMap<String, String> fields=new HashMap<String, String>();
	
	public void setField(String fieldName,String fieldValue) {
		fields.put(fieldName, fieldValue);
	}
	
	public String getField(String fieldName) {
		return fields.get(fieldName);
	}
	
	public void setMessage(String message) {
		String[] _fields=message.split(EnumSeparator.FIELD.getSeparatorString());
		for(String field:_fields) {
			String[] _value=field.split(EnumSeparator.FIELD_VALUE.getSeparatorString());
			fields.put(_value[0], _value[1]);
		}
	}
	
	public String getMessage() {
		String message="";
		Set<String> _fields=fields.keySet();
		boolean first=true;
		for(String field:_fields) {
			if(first) {
				first=false;
			}
			else {
				message+=EnumSeparator.FIELD.getSeparatorString();
			}
			message+=field+EnumSeparator.FIELD_VALUE.getSeparatorString()+fields.get(field);
		}
		return message;
	}

}
