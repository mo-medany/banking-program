package com.sunhill.banking.program.accounts;

import java.util.concurrent.atomic.AtomicInteger;

import com.sunhill.banking.program.domain.Owner;

/**
 * @author medany
 */

public class SavingsAccount extends Account {

	/**
	 * interest rate
	 */
	private double rate;

	/**
	 * {@code SavingsAccount} qualified class name
	 */
	private static final String CLAZZ = SavingsAccount.class.getName();

	public SavingsAccount(Owner account, double balance, double rate) {
		super(account, balance);
		this.rate = rate;
	}

	/**
	 * Increases each saving account with specified interest rate
	 * 
	 * @return 0 when interest provided to all accounts. 1 when any exception thrown
	 */
	public static int provideInterestToAllUsers() {
		AtomicInteger result = new AtomicInteger(0);
		try {
			STORE.findAll(CLAZZ).forEach(account -> {
				((SavingsAccount) account).balance += ((SavingsAccount) account).balance
						* (((SavingsAccount) account).rate / 100);
			});
		} catch (Exception e) {
			e.printStackTrace();
			result.set(1);
		}
		return result.get();
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
