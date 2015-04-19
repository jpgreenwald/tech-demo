package io.swsb.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Created by swsb
 */
@Provider
public class HeaderFilter implements ContainerRequestFilter, ContainerResponseFilter
{
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private ThreadLocal<UUID> reqId = new ThreadLocal<>();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        startTime.set(System.currentTimeMillis());
        reqId.set(UUID.randomUUID());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
    {
        if (startTime.get() != null)
        {
            responseContext.getHeaders().putSingle("elapsed", String.format("%sms", System.currentTimeMillis() - startTime.get()));
        }
        responseContext.getHeaders().putSingle("host", InetAddress.getLocalHost().getHostName());
        responseContext.getHeaders().putSingle("req-id", reqId.get());
    }
}
