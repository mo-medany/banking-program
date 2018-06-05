package com.sunhill.banking.program.db.exceptions;

/**
 * Thrown when trying to find an entry with no present identifier
 * 
 * @author medany
 */
public class IDNotFoundException extends DataStoreException {

	private static final long serialVersionUID = 1906215466949507828L;

	/**
	 * Constructs a {@code IDNotFoundException} object with specified reason
	 * 
	 * @param reason
	 *            description of the exception
	 */
	public IDNotFoundException(String reason) {
		super(reason);
	}
}
