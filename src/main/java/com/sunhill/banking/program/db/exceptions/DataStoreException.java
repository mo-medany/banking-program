package com.sunhill.banking.program.db.exceptions;

/**
 * An exception that provides information on a store access error. Each
 * {@code DataStoreException} provides a string describing the error.
 * 
 * @author medany
 */
public class DataStoreException extends Exception {

	private static final long serialVersionUID = 2443947931804328232L;

	/**
	 * Constructs a {@code DataStoreException} with specified reason
	 * 
	 * @param reason
	 *            description of the exception
	 */
	public DataStoreException(String reason) {
		super(reason);
	}

}
