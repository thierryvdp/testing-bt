package com.coolskan.www.reader.utils;

import java.io.Closeable;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * A convenient class to centralize all operation that should be done on 
 * interface
 * @author Cédric Chappert
 *
 */
public class InterfacesOperations {

	private static final Logger logger = Logger.getLogger(InterfacesOperations.class);

	/**
	 * Execute the method close for all specified {@link Closeable}s only
	 * if {@link Closeable} is not null.<br>
	 * 
	 * @param closables the list of {@link Closeable}
	 */
	public static void closeAll(Closeable... closables) {
		for (Closeable closeable : closables) {
			if (closeable != null) {
				try { 
					closeable.close(); 
				} catch (IOException e) { 
					logger.error("", e); 
				} finally {
					// continue;
				}
			}
		}
	}
	
}
