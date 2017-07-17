package com.paymentator.bankinformationservice.utils;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;

import com.paymentator.bankinformationservice.model.IssuerIdentificationNumberRange;

public class IssuerIdentificationNumberComparableRangeMapCreatorTest {
    private IssuerIdentificationNumberRangeMapCreator mapCreator;
    private Map<String, String> iinRangeByBank;

    @Before
    public void setup() {
        mapCreator = new IssuerIdentificationNumberRangeMapCreator();
        iinRangeByBank = new HashMap<>();
        iinRangeByBank.put("5358-54**", "RolBank");
        iinRangeByBank.put("52", "nBank");
        iinRangeByBank.put("60-61", "kakao sa");
        iinRangeByBank.put("5300-5358", "omg bank lodzki");
    }

    @Test
    public void shouldConvertStringRangeWithAsteriskInto6DigitsIinIntegerRange() {

        Map<IssuerIdentificationNumberRange, String> comparableIinMap =
                mapCreator.createIinRangeMap(iinRangeByBank);
        assertThat(comparableIinMap, IsMapContaining
                .hasEntry(new IssuerIdentificationNumberRange(535800, 549999), "RolBank"));
    }

    @Test
    public void shouldConvertStringRangeInto6DigitsIinIntegerRange() {

        Map<IssuerIdentificationNumberRange, String> comparableIinMap =
                mapCreator.createIinRangeMap(iinRangeByBank);
        assertThat(comparableIinMap, IsMapContaining
                .hasEntry(new IssuerIdentificationNumberRange(530000, 535899), "omg bank lodzki"));
        assertThat(comparableIinMap, IsMapContaining
                .hasEntry(new IssuerIdentificationNumberRange(600000, 619999), "kakao sa"));
    }

    @Test
    public void shouldConvertStringSingleValueInto6DigitsIinIntegerRange() {

        Map<IssuerIdentificationNumberRange, String> comparableIinMap =
                mapCreator.createIinRangeMap(iinRangeByBank);
        assertThat(comparableIinMap, IsMapContaining
                .hasEntry(new IssuerIdentificationNumberRange(520000, 529999), "nBank"));
    }
}
