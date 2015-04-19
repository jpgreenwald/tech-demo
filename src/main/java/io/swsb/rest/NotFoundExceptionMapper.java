package io.swsb.rest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by swsb
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException>
{
    @Override
    public Response toResponse(NotFoundException exception)
    {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
