package com.paymentator.bankinformationservice.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.paymentator.bankinformationservice.model.IssuerIdentificationNumberRange;

public class IssuerIdentificationNumberComparableRangeMapCreatorTest {
    private Map<String, String> iinRangeByBank;
    IssuerIdentificationNumberComparableRangeMapCreator mapCreator;

    @Before
    public void setup() {
        iinRangeByBank = new HashMap<String, String>();
        iinRangeByBank.put("5358-54**", "RolBank");
        iinRangeByBank.put("52", "nBank");
        iinRangeByBank.put("60-61", "kakao sa");
        iinRangeByBank.put("5300-5358", "omg bank lodzki");
        mapCreator = new IssuerIdentificationNumberComparableRangeMapCreator();
    }

    @Test
    public void shouldConvertRangeWithAsteriskInto6DigitsIntegerRange() {
        Map<IssuerIdentificationNumberRange, String> comparableIinMap =
                mapCreator.createIinRangeMap(iinRangeByBank);
        System.out.println(comparableIinMap);
    }
}
