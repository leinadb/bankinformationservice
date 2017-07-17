package com.paymentator.bankinformationservice.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.paymentator.bankinformationservice.utils.IssuerIdentificationNumberToBankIdConverter;

public class IssuerIdentificationNumberToBankIdConverterTest {
    private Map<String, String> iinRangeByBank;
    IssuerIdentificationNumberToBankIdConverter converter;

    @Before
    public void setup() {
        iinRangeByBank = new HashMap<String, String>();
        iinRangeByBank.put("5358-54**", "RolBank");
        iinRangeByBank.put("52", "nBank");
        iinRangeByBank.put("60-61", "kakao sa");
        iinRangeByBank.put("5300-5358", "omg bank lodzki");
        // panByBank.put("67,68,69", "pdk bank wschodni");
        // panByBank.put("70,73,76, 6700-7800, 8800-900*", "gg bank ochrony gier");

        converter = new IssuerIdentificationNumberToBankIdConverter(iinRangeByBank);

    }


    @Test
    public void shouldMatchProperlyForRangeValue() {
        String cardData = "5350";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("omg bank lodzki", bankId);
    }

    @Test
    public void shouldMatchProperlyForEdgeRangeValue() {
        String cardData = "60";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("kakao sa", bankId);
    }

    @Test
    public void shouldMatchProperlyForStrictValue() {
        String cardData = "52";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("nBank", bankId);
    }

    @Test
    public void shouldMatchProperlyForStrictValueWhereInputIsLongerThanIIN() {
        String cardData = "5200";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("nBank", bankId);
    }


    @Test
    public void shouldMatchProperlyForAsteriskValue() {
        String cardData = "5498";


        String bankId = converter.convertToBankId(cardData);

        assertEquals("RolBank", bankId);
    }


    @Test
    public void shouldMatchProperlyForRangeEvenIfCardDataIsLongerThanMatchingRange() {
        String cardData = "535012";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("omg bank lodzki", bankId);
    }

    @Ignore
    @Test
    public void shouldMatchProperlyForCommaSeparatedValue() {
        String cardData = "67";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("pdk bank wschodni", bankId);
    }

    @Ignore
    @Test
    public void shouldMatchProperlyForCommaSeparatedValueWithSpaces() {
        String cardData = "67";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("pdk bank wschodni", bankId);
    }

    @Ignore
    @Test
    public void shouldMatchProperlyForCombinationOfRangeStrictComaSeparatedAndAsteriskValues() {
        String cardData = "9007";

        String bankId = converter.convertToBankId(cardData);

        assertEquals("gg bank ochrony gier", bankId);
    }

}
