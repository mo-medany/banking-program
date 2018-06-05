package com.sunhill.banking.program.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sunhill.banking.program.db.exceptions.IDAlreadyExistsException;
import com.sunhill.banking.program.db.exceptions.IDNotFoundException;
import com.sunhill.banking.program.db.exceptions.TypeAlreadyFoundException;
import com.sunhill.banking.program.db.exceptions.TypeNotFoundException;

/**
 * @author medany
 */

public final class AccountStore {

	/**
	 * {@code AccountStore} instance
	 */
	private static AccountStore INSTANCE;

	/**
	 * default objects store
	 */
	private static Map<String, Map<Object, Object>> STORE;

	/**
	 * don't let anyone instantiate this class.
	 */
	private AccountStore() {
	}

	/**
	 * Initializes {@code AccountStore} instance when method is called for the first
	 * time. This technique ensures that {@code AccountStore} instance is created
	 * only when needed.
	 */
	public static AccountStore getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AccountStore();
			STORE = new HashMap<>();
		}
		return INSTANCE;
	}

	/**
	 * Creates specified type in this store. {@code TypeAlreadyFoundException} is
	 * thrown when specified type is present.
	 * 
	 * @param clazz
	 *            type to create
	 * @throws TypeAlreadyFoundException
	 *             if specified type is present
	 */
	public void create(String clazz) throws TypeAlreadyFoundException {
		if (STORE.containsKey(clazz)) {
			throw new TypeAlreadyFoundException("Error create '" + clazz + "'. type already exists");
		}
		STORE.put(clazz, new HashMap<Object, Object>());
	}

	/**
	 * Associates specified identifier with specified value in this store.
	 * {@code TypeNotFoundException} is thrown when specified type not present,
	 * {@code IDAlreadyExistsException} is thrown when specified identifier already
	 * exists.
	 * 
	 * @param id
	 *            identifier of the value to associate
	 * @param clazz
	 *            value to associate
	 * @return inserted value
	 * @throws TypeNotFoundException
	 *             if specified type not present
	 * @throws IDAlreadyExistsException
	 *             if specified id already exists
	 */
	public Object insert(Object id, Object entry) throws TypeNotFoundException, IDAlreadyExistsException {
		if (!STORE.containsKey(entry.getClass().getName())) {
			throw new TypeNotFoundException("Error insert '" + entry.getClass().getName() + "'. type not fount");
		}

		if (STORE.get(entry.getClass().getName()).containsKey(id)) {
			throw new IDAlreadyExistsException("Error insert '" + id + "'. Already exists");
		}

		Object added = STORE.get(entry.getClass().getName()).put(id, entry);
		return added;
	}

	/**
	 * Returns the value to which specified identifier is associated.
	 * {@code TypeNotFoundException} is thrown when specified type not present,
	 * {@code IDNotFoundException} is thrown when specified identifier not present.
	 * 
	 * @param id
	 *            identifier of value to find
	 * @param clazz
	 *            type of identifier to find
	 * @return value with the specified identifier
	 * @throws TypeNotFoundException
	 *             if specified type not present
	 * @throws IDNotFoundException
	 *             if this identifier not present in database
	 */
	public Object find(Object id, String clazz) throws TypeNotFoundException, IDNotFoundException {
		if (!STORE.containsKey(clazz)) {
			throw new TypeNotFoundException("Error find '" + clazz + "'. type not fount");
		}

		if (!STORE.get(clazz).containsKey(id)) {
			throw new IDNotFoundException("Error find '" + id + "'. does not exists");
		}

		return STORE.get(clazz).get(id);
	}

	/**
	 * Returns all values of specified type. {@code TypeNotFoundException} is thrown
	 * when specified type not present.
	 * 
	 * @param clazz
	 *            type to find all its values
	 * @return all values of specified type
	 * @throws TypeNotFoundException
	 *             if specified type not present
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> findAll(String clazz) throws TypeNotFoundException {
		if (!STORE.containsKey(clazz)) {
			throw new TypeNotFoundException("Error find '" + clazz + "'. type not fount");
		}

		return (Collection<T>) STORE.get(clazz).values();
	}
}
