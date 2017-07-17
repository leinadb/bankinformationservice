package com.paymentator.bankinformationservice.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IssuerIdentificationNumberExtractorTest {


    @Test
    public void shouldExtractFirst6DigitsFromPrimaryAccountNumber() {
        IssuerIdentificationNumberExtractor extractor = new IssuerIdentificationNumberExtractor();
        String iin = extractor.getIinFromPan("1200233012210000");
        assertEquals("120023", iin);
    }

    @Test
    public void shouldReturnInputNumberWithPaddingZeroesIfItsLengthIsSmallerThanIinItself() {
        IssuerIdentificationNumberExtractor extractor = new IssuerIdentificationNumberExtractor();
        String iin = extractor.getIinFromPan("12002");
        assertEquals("120020", iin);
    }

    @Test
    public void shouldReturnNumberWithoutAnyWhitespaces() {
        IssuerIdentificationNumberExtractor extractor = new IssuerIdentificationNumberExtractor();
        String iin = extractor.getIinFromPan(" 12 0   0 2 ");
        assertEquals("120020", iin);
    }
}
