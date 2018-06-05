package com.sunhill.banking.program.db.exceptions;

/**
 * Thrown when trying to persist an entry with already existing identifier
 * 
 * @author medany
 */
public class IDAlreadyExistsException extends DataStoreException {

	private static final long serialVersionUID = 8636327993441764263L;

	/**
	 * Constructs a {@code IDAlreadyExistsException} object with specified reason
	 * 
	 * @param reason
	 *            description of the exception
	 */
	public IDAlreadyExistsException(String reason) {
		super(reason);
	}
}
