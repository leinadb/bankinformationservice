package com.paymentator.bankinformationservice.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.paymentator.bankinformationservice.model.IssuerIdentificationNumberRange;

public class IssuerIdentificationNumberComparableRangeMapCreator {

    public Map<IssuerIdentificationNumberRange, String> createIinRangeMap(
            Map<String, String> iinRangeByBankId) {
        Map<IssuerIdentificationNumberRange, String> map = new HashMap<>();
        for (Entry<String, String> entry : iinRangeByBankId.entrySet()) {
            String key2 = entry.getKey();
            if (entry.getKey().contains("*") && entry.getKey().contains("-")) {
                map.put(handleEnhancedRangeValue(entry.getKey()), entry.getValue());
            } else if (entry.getKey().contains("-")) {
                map.put(handleSimpleRangeValue(entry.getKey()), entry.getValue());
            } else {
                map.put(handleStrictValue(entry.getKey()), entry.getValue());
            }
        }

        return map;
    }



    private IssuerIdentificationNumberRange handleStrictValue(String rangeValue) {
        String[] rangeNumbers = {rangeValue, rangeValue};
        return padWithValues(rangeNumbers);
    }



    private IssuerIdentificationNumberRange handleSimpleRangeValue(String rangeValue) {
        String[] rangeNumbers = rangeValue.split("-");
        return padWithValues(rangeNumbers);
    }

    private IssuerIdentificationNumberRange handleEnhancedRangeValue(String rangeValue) {
        String[] rangeNumbers = rangeValue.replace("*", "9").split("-");
        return padWithValues(rangeNumbers);
    }

    private IssuerIdentificationNumberRange padWithValues(String[] rangeNumbers) {
        String minValue = rangeNumbers[0];
        if (minValue.length() < IssuerIdentificationNumberRange.IIN_LENGTH) {
            minValue =
                    StringUtils.rightPad(minValue, IssuerIdentificationNumberRange.IIN_LENGTH, '0');
        }
        Integer firstNumber = Integer.valueOf(minValue);
        String maxValue = rangeNumbers[1];
        if (maxValue.length() < IssuerIdentificationNumberRange.IIN_LENGTH) {
            maxValue =
                    StringUtils.rightPad(maxValue, IssuerIdentificationNumberRange.IIN_LENGTH, '9');
        }
        Integer lastNumber = Integer.valueOf(maxValue);
        return new IssuerIdentificationNumberRange(firstNumber, lastNumber);
    }

}
