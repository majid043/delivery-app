package com.bayzdelivery.enums;

public enum OrderStatuses {
	
	O("OPEN"),
    C("CLOSED"),
    D("DELAYED");

    private final String status;

    private OrderStatuses(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
