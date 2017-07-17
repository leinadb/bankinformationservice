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
import com.paymentator.bankinformationservice.model.IssuerIdentificationNumberRange;
import com.paymentator.bankinformationservice.utils.IssuerIdentificationNumberExtractor;
import com.paymentator.bankinformationservice.utils.IssuerIdentificationNumberRangeMapCreator;
import com.paymentator.bankinformationservice.utils.IssuerIdentificationNumberToBankIdConverter;

public class BankApplication extends Application implements IBankApplication {
    private static final Logger log = LoggerFactory.getLogger(BankApplication.class);

    private HashMap<String, String> iinRangeByBankId;
    private Map<IssuerIdentificationNumberRange, String> iinNumberRangeByBank;
    private IssuerIdentificationNumberExtractor iinExtractor;
    private IssuerIdentificationNumberToBankIdConverter iinConverter;
    private IssuerIdentificationNumberRangeMapCreator iinMapConverter;

    public BankApplication() {
        iinRangeByBankId = new HashMap<String, String>();
        iinRangeByBankId.put("5358-54**", "RolBank");
        iinRangeByBankId.put("52", "nBank");
        iinRangeByBankId.put("60-61", "kakao sa");
        iinRangeByBankId.put("5300-5358", "omg bank lodzki");

        iinExtractor = new IssuerIdentificationNumberExtractor();
        iinConverter = new IssuerIdentificationNumberToBankIdConverter(iinRangeByBankId);
        iinMapConverter = new IssuerIdentificationNumberRangeMapCreator();
        iinNumberRangeByBank = iinMapConverter.createIinRangeMap(iinRangeByBankId);
    }

    @Override
    public boolean isBankSuspended(String bankName) {
        return !iinRangeByBankId.containsValue(bankName);
    }

    @Override
    public Map<String, String> getAllBanks() {
        return iinRangeByBankId;
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
            String extractedIin = iinExtractor.getIinFromPan(number);
            String bankId = searchThroughIinRangeNumbers(extractedIin);
            if (bankId != null)
                cardNumberByBankId.put(number, bankId);
        });
        return cardNumberByBankId;
    }

    private String searchThroughIinRangeNumbers(String extractedIin) {
        Integer iinNumber = Integer.valueOf(extractedIin);
        for (IssuerIdentificationNumberRange range : iinNumberRangeByBank.keySet()) {
            if (range.getMinRange() <= iinNumber && range.getMaxRange() > iinNumber)
                return iinNumberRangeByBank.get(range);
        }
        return null;
    }

}
