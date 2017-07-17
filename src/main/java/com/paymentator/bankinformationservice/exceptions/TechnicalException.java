package com.paymentator.bankinformationservice.exceptions;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class TechnicalException implements ExceptionMapper<ServerErrorException> {

    @Override
    public Response toResponse(ServerErrorException exception) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity("Service is facing some technical issues.").build();
    }

}
