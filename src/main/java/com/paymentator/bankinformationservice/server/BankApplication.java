package com.paymentator.bankinformationservice.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paymentator.bankinformationservice.exceptions.NoSuchBankException;
import com.paymentator.bankinformationservice.exceptions.TechnicalException;
import com.paymentator.bankinformationservice.utils.IssuerIdentificationNumberExtractor;
import com.paymentator.bankinformationservice.utils.IssuerIdentificationNumberToBankIdConverter;

public class BankApplication extends Application implements IBankApplication {
    private static final Logger log = LoggerFactory.getLogger(BankApplication.class);

    private HashMap<String, String> iinRangeByBank;
    private IssuerIdentificationNumberExtractor iinExtractor;
    private IssuerIdentificationNumberToBankIdConverter iinConverter;

    public BankApplication() {
        iinRangeByBank = new HashMap<String, String>();
        iinRangeByBank.put("5358-54**", "RolBank");
        iinRangeByBank.put("52", "nBank");
        iinRangeByBank.put("60-61", "kakao sa");
        iinRangeByBank.put("5300-5358", "omg bank lodzki");

        iinExtractor = new IssuerIdentificationNumberExtractor();
        iinConverter = new IssuerIdentificationNumberToBankIdConverter(iinRangeByBank);
    }

    @Override
    public boolean isBankSuspended(String bankName) {
        return !iinRangeByBank.containsValue(bankName);
    }

    @Override
    public Map<String, String> getAllBanks() {
        return iinRangeByBank;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> result = new HashSet<>();
        result.add(BankResource.class);
        result.add(TechnicalException.class);
        result.add(NoSuchBankException.class);
        return result;
    }

    @Override
    public Map<String, String> getAllQualifyingCardsWithTheirBankId(Set<String> cardData) {
        Map<String, String> cardNumberByBankId = new ConcurrentHashMap<>();
        cardData.stream().parallel().forEach(number -> {
            String extractedIIN = iinExtractor.getIinFromPan(number);
            String bankId = iinConverter.convertToBankId(extractedIIN);
            if (bankId != null)
                cardNumberByBankId.put(number, bankId);
        });
        return cardNumberByBankId;
    }

}
