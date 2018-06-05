package com.sunhill.banking.program.accounts;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import com.sunhill.banking.program.accounts.Account;
import com.sunhill.banking.program.accounts.SavingsAccount;
import com.sunhill.banking.program.db.AccountStore;
import com.sunhill.banking.program.db.exceptions.IDNotFoundException;
import com.sunhill.banking.program.db.exceptions.TypeNotFoundException;
import com.sunhill.banking.program.domain.Owner;

/**
 * @author medany
 */

public class SavingAccountTest {

	protected AccountStore STORE = AccountStore.getInstance();
	private static final String CLAZZ = SavingsAccount.class.getName();

	@Test
	public void shouldCreateSaveingsAccount() {

		// create new account // should succeed
		Account actual = new SavingsAccount(new Owner(100, "Mohamed"), 1000D, 10D);

		Account expected = null;
		try {
			expected = (SavingsAccount) STORE.find(System.identityHashCode(actual), CLAZZ);
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(expected, actual);

	}

	@Test
	public void shouldWithdrawMoney() {

		Account saving = new SavingsAccount(new Owner(200, "Mohamed"), 10000D, 11D);

		// withdraw all balance // should succeed
		Assert.assertEquals(0, saving.withdraw(500D));

		double expected = 0;
		try {
			expected = ((SavingsAccount) STORE.find(System.identityHashCode(saving), CLAZZ)).balanceCheck();
		} catch (TypeNotFoundException | IDNotFoundException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(expected, 9500D, 0);
	}

	@Test
	public void shouldNotWithdrawMoneyMoreThanBalance() {

		Account saving = new SavingsAccount(new Owner(300, "Mohamed"), 10000D, 12D);

		// withdraw more than current balance // should not succeed
		Assert.assertEquals(1, saving.withdraw(11000D));

	}

	@Test
	public void shouldProvideInterestToAllSavingsAccounts() {

		AtomicReference<String> actual = new AtomicReference<>("");
		AtomicReference<String> expected = new AtomicReference<>("");

		try {
			STORE.findAll(CLAZZ).forEach(account -> {
				double newBalance = ((SavingsAccount) account).balance
						+ (((SavingsAccount) account).balance * (((SavingsAccount) account).getRate() / 100));
				actual.set(actual.get() + newBalance + ",");
			});
		} catch (TypeNotFoundException e) {
			e.printStackTrace();
		}

		// provide interest rate to all accounts // should succeed
		Assert.assertEquals(0, SavingsAccount.provideInterestToAllUsers());

		try {
			STORE.findAll(CLAZZ).forEach(account -> {
				expected.set(expected.get() + ((SavingsAccount) account).balance + ",");
			});
		} catch (TypeNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(expected.get(), actual.get());

	}

	@Test
	public void shouldChnageInterestRate() {

		SavingsAccount saving = new SavingsAccount(new Owner(800, "Mohamed"), 10000D, 12D);

		// initial rate
		Assert.assertEquals(12D, saving.getRate(), 0);

		saving.setRate(15D);

		// rate after change
		Assert.assertEquals(15D, saving.getRate(), 0);

	}
}
