package com.paymentator.bankinformationservice.server;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(value = "/banks")
public class BankResource {
    private static final Logger log = LoggerFactory.getLogger(BankResource.class);

    public static final String BANK_CARD_DATA = "card-data";

    @Context
    private Application bankStatic;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBanks(
            @QueryParam(BANK_CARD_DATA) Set<String> cardData) {
        IBankApplication bankApplication = (IBankApplication) bankStatic;
        GenericEntity<Map<String, String>> resultMap;
        if (cardData.isEmpty()) {
            resultMap = new GenericEntity<Map<String, String>>(bankApplication.getAllBanks()) {};
        } else {
            resultMap = new GenericEntity<Map<String, String>>(
                    bankApplication.getAllQualifyingCardsWithTheirBankId(cardData)) {};
        }
        return Response.ok(resultMap).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isBankSuspended(@PathParam(value = "id") String bankId) {
        IBankApplication bankApplication = (IBankApplication) bankStatic;

        boolean isBankSuspended = bankApplication.isBankSuspended(bankId);
        if (!isBankSuspended) {
            GenericEntity<Boolean> genericEntity = new GenericEntity<Boolean>(isBankSuspended) {};
            return Response.ok(genericEntity).build();
        } else {
            throw new NotFoundException();
        }
    }

}
