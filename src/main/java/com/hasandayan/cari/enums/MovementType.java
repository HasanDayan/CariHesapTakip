package com.hasandayan.cari.enums;

public enum MovementType {

	LOAN("Borç İşlemi", "B"), 
	BORROW("Alacak İşlemi", "A"), 
	CLOSED("Kapalı", "-");

	private String value;

	private String shortValue;

	private MovementType(String value, String shortValue) {
		this.value = value;
		this.shortValue = shortValue;
	}

	public String getValue() {
		return value;
	}

	public String getShortValue() {
		return shortValue;
	}

	public static MovementType getByShortValue(String shortValue) {

		for (MovementType type : MovementType.values()) {
			if (type.getShortValue().equals(shortValue)) {
				return type;
			}
		} 

		return null;
	}
}
