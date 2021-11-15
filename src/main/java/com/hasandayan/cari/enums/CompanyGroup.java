package com.hasandayan.cari.enums;

public enum CompanyGroup {
	
	CUSTOMER("MÜŞTERİ"),
	WHOLESALER("TOPTANCI"),
	PRIVATE("ÖZEL"),
	OVERHEADS("GENEL_GİDERLER"),
	VOUCHERS("ÇEKLER"),
	CASE("KASA"),
	BANKS("BANKALAR"),
	PERSONAL("PERSONEL");
	
	private String value;
	
	private CompanyGroup(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
