package com.sunhill.banking.program.accounts;

import com.sunhill.banking.program.db.AccountStore;
import com.sunhill.banking.program.db.exceptions.IDAlreadyExistsException;
import com.sunhill.banking.program.db.exceptions.TypeAlreadyFoundException;
import com.sunhill.banking.program.db.exceptions.TypeNotFoundException;
import com.sunhill.banking.program.domain.Owner;

/**
 * @author medany
 */

public abstract class Account {

	protected Owner accountOwner;
	protected double balance;

	/**
	 * {@code AccountStore} instance
	 */
	protected static AccountStore STORE = AccountStore.getInstance();

	public Account(Owner accountOwner, double balance) {
		this.accountOwner = accountOwner;
		this.balance = balance;
		save();
	}

	/**
	 * Check current account balance
	 * 
	 * @return current balance of this account
	 */
	public double balanceCheck() {
		return this.balance;
	}

	/**
	 * Increase current balance by specified amount
	 * 
	 * @param amount
	 *            specified amount to deposit
	 * @return 0 when deposit succeeded. 1 when amount has negative value
	 */
	public int deposit(double amount) {
		if (amount > 0) {
			balance += amount;
			return 0;
		}
		return 1;
	}

	/**
	 * Increase current balance by specified amount
	 * 
	 * @param amount
	 *            specified amount to withdraw
	 * @return 0 when withdraw succeeded. 1 when amount to withdraw exceeds current
	 *         balance
	 */
	public int withdraw(double amount) {
		if (balance >= amount) {
			balance -= amount;
			return 0;
		}
		return 1;
	}

	/**
	 * Saves newly instantiated Account object to AccountStore. should only run from
	 * constructor.
	 * 
	 * @return
	 */
	private Account save() {
		try {
			STORE.insert(System.identityHashCode(this), this);
		} catch (TypeNotFoundException | IDAlreadyExistsException e) {
			try {
				STORE.create(this.getClass().getName());
				STORE.insert(System.identityHashCode(this), this);
			} catch (TypeAlreadyFoundException | IDAlreadyExistsException | TypeNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return this;
	}

}
