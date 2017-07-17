package com.paymentator.bankinformationservice.model;

public class Bank {
    private String bankId;
    private String issuerIdentificationNumber;
    private boolean suspended;

    public Bank(String bankId, String iin, boolean suspended) {
        this.bankId = bankId;
        this.issuerIdentificationNumber = iin;
        this.suspended = suspended;
    }
}
