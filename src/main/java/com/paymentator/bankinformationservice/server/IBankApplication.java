package com.paymentator.bankinformationservice.server;

import java.util.Map;
import java.util.Set;


public interface IBankApplication {
    Map<String, String> getAllBanks();

    Map<String, String> getAllQualifyingCardsWithTheirBankId(Set<String> cardData);

    boolean isBankSuspended(String id);


}
