package irl.common.tool;

public class Convertion {

	public static String sEncrypt(String clearString){
		String pwe = "";
		try {
			java.security.MessageDigest d =null;
			d = java.security.MessageDigest.getInstance("SHA-1");
			d.reset();
			d.update(clearString.getBytes());
			byte[] b =  d.digest();
			for (int i = 0; i < b.length; i++) {
				pwe = pwe + b[i];
			}
		} catch (Exception e) {
			System.out.println("sEncrypt Exceptione :" + e);
		}
		return pwe;
	}

}
