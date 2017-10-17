package de.bonprix.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This is the JAX-RS provider class defining the ExceptionMapper for all exceptions inheriting the WebserviceException. The class simply creates a response
 * with the defined HTTP code and a JSON-serialized version of the exception.
 * 
 * @author cthiel
 *
 */
@Provider
public class WebserviceExceptionMapper implements ExceptionMapper<WebserviceException> {

    @Override
    public Response toResponse(WebserviceException exception) {
        return Response.status(exception.getResponse()
            .getStatus())
            .entity(exception)
            .header(WebserviceException.HEADER_NAME, exception.getClass()
                .getName())
            .build();
    }

}
