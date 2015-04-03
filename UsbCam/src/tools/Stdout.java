package tools;
/******************************************************
 * File: Stdout.java.java
 * created 24.07.2001 21:44:46 by David Fischer, fischer@d-fischer.com
 * Description: utility class for standard output
 */


public class Stdout
{

	public static void log(String msg) {
		System.out.println(msg);
	}


	public static void logAndAbortException(Exception e) {
		log("" + e.toString());
		flush();
		System.exit(0);
	}

	public static void logAndAbortException(String message,Exception e) {
		log(message + " " + e.toString());
		flush();
		System.exit(0);
	}

	public static void logAndAbortException(String message) {
		log(message);
		flush();
		System.exit(0);
	}

	public static void flush() {
		System.out.flush();
	}

}
