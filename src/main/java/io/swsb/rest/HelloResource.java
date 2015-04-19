package io.swsb.rest;

import io.swsb.HelloWorldService;
import io.swsb.entity.User;
import org.apache.bval.guice.Validate;
import org.codehaus.jackson.map.annotate.JsonView;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by swsb
 */
@GZIP
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
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

    @POST
    public User make()
    {
        return helloWorldService.createUser();
    }

    @JsonView(Views.Public.class)
    @Logged
    @Validate
    @GET
    @Path("/public/{name}")
    public User echo(@PathParam("name") @NotNull @Size(min = 3) String name)
    {
        User user = new User();
        user.setName("public data");
        user.setId(1L);
        return user;
    }

    @Cache(isPrivate = true, noStore = true, maxAge = 10)
    @Logged
    @Validate
    @GET
    @Path("/private/{name}")
    public User echoPrivate(@PathParam("name") @NotNull @Size(min = 3) String name)
    {
        User user = new User();
        user.setName("public data");
        user.setId(1L);
        return user;
    }
}
