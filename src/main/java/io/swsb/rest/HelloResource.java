package io.swsb.rest;

import io.swsb.HelloWorldService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by swsb
 */
@Path("/")
public class HelloResource
{
    @Inject
    private HelloWorldService helloWorldService;

    @Inject
    private HttpServletRequest servletRequest;

    @GET
    public String hello()
    {
        return "jax-rs" + helloWorldService.sayHello();
    }
}
