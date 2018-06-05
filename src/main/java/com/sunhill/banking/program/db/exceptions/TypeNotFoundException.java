package com.sunhill.banking.program.db.exceptions;

/**
 * Thrown when trying to manipulate value with no present type
 * 
 * @author medany
 */
public class TypeNotFoundException extends DataStoreException {

	private static final long serialVersionUID = 462412272895849755L;

	/**
	 * Constructs {@code TypeNotFoundException} with specified reason
	 * 
	 * @param reason
	 *            description of the exception
	 */
	public TypeNotFoundException(String reason) {
		super(reason);
	}
}
