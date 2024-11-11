package io.tacsio.config;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorsHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        if (exception instanceof IllegalArgumentException) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof NotAuthorizedException) {
            status = Response.Status.UNAUTHORIZED;
        }

        return Response.status(status).entity(error).build();
    }

}
