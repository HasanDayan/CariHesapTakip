package com.hasandayan.cari.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CompanyMovementDTO {
	
	private String id;
	
	private String date = LocalDate.now().toString();
	
	private String type;
	
	private String mode;
	
	private String productType;
	
	private String desc;
	
	private BigDecimal amount = new BigDecimal("0.00");

	private BigDecimal quantity = new BigDecimal("0.00");

	private String unit;
	
	private BigDecimal unitPrice = new BigDecimal("0.00");
	
	private BigDecimal balance = new BigDecimal("0.00");
	
	private Long dataTimestamp;
	
	private String companyId;
	
	private String companyName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getDataTimestamp() {
		return dataTimestamp;
	}

	public void setDataTimestamp(Long dataTimestamp) {
		this.dataTimestamp = dataTimestamp;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
