package com.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SensorUnavailableExceptionMapper
        implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException exception) {

        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            403,
            "https://myuniversity.edu/api/docs/errors"
        );

        return Response.status(Status.FORBIDDEN)
                .entity(errorMessage)
                .build();
    }
}
