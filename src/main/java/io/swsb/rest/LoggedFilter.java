package io.swsb.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by swsb
 */
@Logged
@Provider
public class LoggedFilter implements ContainerResponseFilter
{

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
    {
        responseContext.getHeaders().putSingle("logged", "true");
    }
}
