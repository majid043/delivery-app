package com.bayzdelivery.enums;

public enum Devices {
	
	M("MOBILE"),
    W("WEB");

    private final String device;

    private Devices(String device) {
        this.device = device;
    }

    public String getDevice() {
        return device;
    }
}
