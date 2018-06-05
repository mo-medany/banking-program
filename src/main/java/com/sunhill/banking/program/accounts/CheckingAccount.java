package com.sunhill.banking.program.accounts;

import com.sunhill.banking.program.db.exceptions.IDNotFoundException;
import com.sunhill.banking.program.db.exceptions.TypeNotFoundException;
import com.sunhill.banking.program.domain.Owner;

/**
 * @author medany
 */

public class CheckingAccount extends Account {

	/**
	 * {@code CheckingAccount} qualified class name
	 */
	private static final String CLAZZ = CheckingAccount.class.getName();

	public CheckingAccount(Owner account, double balance) {
		super(account, balance);
	}

	/**
	 * Allow withdraw more money than current balance, but also restricted by a
	 * limit
	 */
	@Override
	public int withdraw(double amount) {
		if (balance + accountOwner.getChekingWithdrawLimit() > 0D) {
			balance -= amount;
			return 0;
		}
		return 1;
	}

	/**
	 * 
	 * @param account
	 *            account to transfer money to
	 * @param amount
	 *            amount to transfer
	 * @return 0 if transfer succeeded. 1 when amount exceeds balance limit. 2 when
	 *         specified account not found.
	 */
	public int transferMoney(int account, double amount) {
		try {
			Account to = (CheckingAccount) STORE.find(account, CLAZZ);

			if (super.withdraw(amount) == 0)
				return to.deposit(amount);
			return 1;
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
			return 2;
		}
	}
}
