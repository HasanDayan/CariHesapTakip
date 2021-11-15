package com.hasandayan.cari.enums;

public enum MovementMode {
	
	PURCHASE("ALIŞ"),
	SALE("SATIŞ"),
	OPEN_ACCOUNT("AÇIK HESAP"),
	RECEIPT("DEKONT"),
	TRANSFER("DEVİR"),
	BILL("FATURA"),
	MONEY_TRANSFER("HAVALE"),
	CREDIT_CARD("KREDİ KARTI"),
	COST("MASRAF"),
	CONSENSUS("MUTABAKAT"),
	CASH("NAKİT"),
	ADVANCE_PAYMENT("PEŞİNAT"),
	POLICY("POLİÇE"),
	BOND("SENET"),
	INSTALLMENT("TAKSİT"),
	INTEREST("VADE FARKI"),
	VOUCHER("ÇEK"),
	RETURN("İADE"),
	WORKMANSHIP("İŞÇİLİK"),
	PAY_SLIP("MAKBUZ");
	
	private String value;
	
	private MovementMode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static MovementMode getByValue(String value) {
		
		for (MovementMode mode : MovementMode.values()) {
			if(mode.getValue().equals(value)) {
				return mode;
			}
		}
		
		return null;
	}

}
