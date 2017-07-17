package com.paymentator.bankinformationservice.utils;

public class IssuerIdentificationNumberExtractor {
    private static final int IIN_LENGTH = 6;

    public String getIinFromPan(String primaryAccountNumber) {
        String panWithoutWhitespaces = primaryAccountNumber.trim().replace(" ", "");
        if (panWithoutWhitespaces.length() > 6) {
            int firstNumber = 0;
            return panWithoutWhitespaces.substring(firstNumber, IIN_LENGTH);
        } else
            return panWithoutWhitespaces;
    }

}
