package com.bayzdelivery.enums;

public enum PersonType {
	
	C("CUSTOMER"),
    D("DELIVERYMAN");

    private final String personType;

    private PersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonType() {
        return personType;
    }
}
