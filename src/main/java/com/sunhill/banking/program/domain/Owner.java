package com.sunhill.banking.program.domain;

/**
 * @author medany
 */

public class Owner {

	private long id;
	private String name;
	private double chekingWithdrawLimit;

	public Owner(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getChekingWithdrawLimit() {
		return chekingWithdrawLimit;
	}

	public void setChekingWithdrawLimit(double chekingWithdrawLimit) {
		this.chekingWithdrawLimit = chekingWithdrawLimit;
	}
}
