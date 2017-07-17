package com.paymentator.bankinformationservice.utils;

import java.util.Map;

public class IssuerIdentificationNumberToBankIdConverter {

    private Map<String, String> iinRangeByBankId;

    public IssuerIdentificationNumberToBankIdConverter(Map<String, String> panByBank) {
        this.iinRangeByBankId = panByBank;
    }

    public String convertToBankId(String iin) {
        String result = null;
        for (String key : iinRangeByBankId.keySet()) {
            if (key.contains("*") && key.contains("-")) {
                result = handleEnhancedRangeValue(iin, result, key);
            } else if (key.contains("-")) {
                result = handleSimpleRangeValue(iin, result, key);
            } else if (iin.startsWith(key)) {
                result = iinRangeByBankId.get(key);
            }
        }
        return result;
    }

    private String handleSimpleRangeValue(String iin, String result, String key) {
        String[] rangeNumbers = key.split("-");
        return checkIfIinFitsInRange(iin, result, key, rangeNumbers);
    }

    private String handleEnhancedRangeValue(String iin, String result, String key) {
        String newKey = key.replace("*", "9");
        String[] rangeNumbers = newKey.split("-");
        return checkIfIinFitsInRange(iin, result, key, rangeNumbers);
    }

    private String checkIfIinFitsInRange(String iin, String result, String key,
            String[] rangeNumbers) {
        Integer firstNumber = Integer.valueOf(rangeNumbers[0]);
        Integer lastNumber = Integer.valueOf(rangeNumbers[1]);
        String actualIinToCompare = iin;
        if (checkIfIinHasToBeShortenedInOrderToCompareWithEdgeValue(iin, lastNumber)) {
            actualIinToCompare = iin.substring(0, lastNumber.toString().length());
        }
        if (iinIsBetweenRangeValue(firstNumber, lastNumber, actualIinToCompare))
            result = iinRangeByBankId.get(key);
        return result;
    }

    private boolean iinIsBetweenRangeValue(Integer firstNumber, Integer lastNumber,
            String actualIinToCompare) {
        return Integer.valueOf(actualIinToCompare) >= firstNumber
                && Integer.valueOf(actualIinToCompare) < lastNumber;
    }

    private boolean checkIfIinHasToBeShortenedInOrderToCompareWithEdgeValue(String iin,
            Integer lastNumber) {
        return iin.length() > lastNumber.toString().length();
    }

}
