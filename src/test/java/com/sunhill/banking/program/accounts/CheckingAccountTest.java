package com.sunhill.banking.program.accounts;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.sunhill.banking.program.db.AccountStore;
import com.sunhill.banking.program.db.exceptions.IDNotFoundException;
import com.sunhill.banking.program.db.exceptions.TypeNotFoundException;
import com.sunhill.banking.program.domain.Owner;

/**
 * @author medany
 */

public class CheckingAccountTest {

	protected AccountStore STORE = AccountStore.getInstance();
	private static final String CLAZZ = CheckingAccount.class.getName();

	@Test
	public void shouldCreateCheckingAccount() {

		// create new account // should succeed
		Account actual = new CheckingAccount(new Owner(100, "Mohamed"), 1000D);

		Account expected = null;
		try {
			expected = (CheckingAccount) STORE.find(System.identityHashCode(actual), CLAZZ);
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(expected, actual);

	}

	@Test
	public void shouldWithdrawMoneyWithinLimit() {

		Account checking = new CheckingAccount(new Owner(200, "Mohamed"), 10000D);

		// withdraw money within balance limit // should succeed
		Assert.assertEquals(0, checking.withdraw(12000D));

		double expected = 0;
		try {
			expected = ((CheckingAccount) STORE.find(System.identityHashCode(checking), CLAZZ)).balanceCheck();
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(expected, -2000D, 0);
	}

	@Test
	public void shouldWithdrawMoneyMoreThanBalanceWithinLimit() {

		Owner owner = new Owner(300, "Mohamed");
		owner.setChekingWithdrawLimit(5000D);

		Account checking = new CheckingAccount(owner, 10000D);

		// withdraw all balance // should succeed
		Assert.assertEquals(0, checking.withdraw(10000D));

		// withdraw more than balance within limit // should succeed
		Assert.assertEquals(0, checking.withdraw(5000D));

		// withdraw more than balance exceeding limit // should not succeed
		Assert.assertEquals(1, checking.withdraw(5000D));

		double expected = 0;
		try {
			expected = ((CheckingAccount) STORE.find(System.identityHashCode(checking), CLAZZ)).balanceCheck();
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(expected, -5000D, 0);

	}

	@Test
	public void shouldTransferToAnotherAccount() {

		Owner owner = new Owner(400, "Mohamed");
		owner.setChekingWithdrawLimit(5000D);

		CheckingAccount checking = new CheckingAccount(owner, 10000D);

		Owner owner1 = new Owner(500, "Mohamed");
		owner.setChekingWithdrawLimit(5000D);

		CheckingAccount checking1 = new CheckingAccount(owner1, 12000D);

		// transfer money from account to another // should succeed
		Assert.assertEquals(0, checking.transferMoney(System.identityHashCode(checking1), 5000D));

		// check withdrawal from first account
		double expected = 0;
		try {
			expected = ((CheckingAccount) STORE.find(System.identityHashCode(checking), CLAZZ)).balanceCheck();
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(expected, 5000D, 0);

		// check deposit to second account
		expected = 0;
		try {
			expected = ((CheckingAccount) STORE.find(System.identityHashCode(checking1), CLAZZ)).balanceCheck();
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(expected, 17000D, 0);
	}

	@Test
	public void shouldNotTransferMoreThanBalanceLimit() {

		Owner owner = new Owner(600, "Mohamed");
		owner.setChekingWithdrawLimit(1000D);

		CheckingAccount checking = new CheckingAccount(owner, 1000D);

		Owner owner1 = new Owner(700, "Mohamed");
		owner.setChekingWithdrawLimit(5000D);

		CheckingAccount checking1 = new CheckingAccount(owner1, 2000D);

		// transfer money exceeding account balance // should not succeed
		Assert.assertEquals(1, checking.transferMoney(System.identityHashCode(checking1), 5000D));

	}

}
