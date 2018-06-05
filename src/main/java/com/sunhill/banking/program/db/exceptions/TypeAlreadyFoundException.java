package com.sunhill.banking.program.db.exceptions;

/**
 * Thrown when trying to create an already existing type
 * 
 * @author medany
 */
public class TypeAlreadyFoundException extends DataStoreException {

	private static final long serialVersionUID = 9159123551097480618L;

	/**
	 * Constructs {@code TypeAlreadyFoundException} with specified reason
	 * 
	 * @param reason
	 *            description of the exception
	 */
	public TypeAlreadyFoundException(String reason) {
		super(reason);
	}
}
