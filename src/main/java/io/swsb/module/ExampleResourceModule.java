package io.swsb.module;

import io.swsb.rest.HelloResource;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;

/**
 * Created by swsb
 */
public class ExampleResourceModule extends RequestScopeModule
{
    @Override
    protected void configure()
    {
        super.configure();
        bind(HelloResource.class);
    }
}
