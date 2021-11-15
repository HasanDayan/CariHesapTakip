package com.hasandayan.cari.pojo;

import java.math.BigDecimal;

public class CompanyDTO {
	
	private String id;
	
	private String name;
	
	private String official;
	
	private String group;

	private String otherInfo;
	
	private BigDecimal loan = new BigDecimal("0.00");

	private BigDecimal borrow = new BigDecimal("0.00");

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfficial() {
		return official;
	}

	public void setOfficial(String official) {
		this.official = official;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public BigDecimal getLoan() {
		return loan;
	}

	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}

	public BigDecimal getBorrow() {
		return borrow;
	}

	public void setBorrow(BigDecimal borrow) {
		this.borrow = borrow;
	}

	public BigDecimal getBalance() {
		return loan.subtract(borrow);
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String findColorClass(BigDecimal amount) {

		int result = amount.compareTo(BigDecimal.ZERO);

		if (result == 0) {
			return "text-success";
		} else if (result == 1) {
			return "text-danger";
		} else {
			return "text-primary";
		}
	}

	public String findMovementType(BigDecimal amount) {
		int result = amount.compareTo(BigDecimal.ZERO);

		if (result == 0) {
			return "-";
		} else if (result == 1) {
			return "B";
		} else {
			return "A";
		}
	}
	
	public String findBalanceState(BigDecimal amount) {
		int result = amount.compareTo(BigDecimal.ZERO);

		if (result == 0) {
			return "KAPALI";
		} else if (result == 1) {
			return "BİZE BORÇLU";
		} else {
			return "BİZDEN ALACAKLI";
		}
	}
}
