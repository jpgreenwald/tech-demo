package io.swsb;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import io.swsb.managed.Managed;
import io.swsb.module.AutoStartModule;
import io.swsb.module.ExampleResourceModule;
import io.swsb.rest.*;
import org.apache.bval.guice.ValidationModule;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Singleton;
import java.util.List;
import java.util.Properties;

/**
 * Created by swsb
 */
public class Bootstrap
{
    public static void main(String[] args) throws Exception
    {

        JpaPersistModule jpaPersistModule = new JpaPersistModule("sample-ds");
        Properties properties = new Properties();
        properties.setProperty(PersistenceUnitProperties.JDBC_URL, "jdbc:h2:./test");
        properties.setProperty(PersistenceUnitProperties.JDBC_DRIVER, "org.h2.Driver");
        properties.setProperty(PersistenceUnitProperties.JDBC_PASSWORD, "");
        properties.setProperty(PersistenceUnitProperties.LOGGING_LEVEL, "FINEST");
        properties.setProperty(PersistenceUnitProperties.CACHE_SHARED_DEFAULT, "false");
        properties.setProperty(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        properties.setProperty("eclipselink.query-results-cache", "false");
        properties.setProperty("eclipselink.refresh", "true");
        jpaPersistModule.properties(properties);

        Injector injector = Guice.createInjector(new ServletModule()
        {
            @Override
            protected void configureServlets()
            {
                bind(HelloResource.class);
                bind(ConstraintViolationExceptionMapper.class);
                bind(NotFoundExceptionMapper.class);
                bind(HeaderFilter.class);
                bind(LoggedFilter.class);

                bind(HttpServletDispatcher.class).in(Singleton.class);

                filter("/*").through(PersistFilter.class);
                serve("/*").with(HttpServletDispatcher.class);
            }
        }, new AutoStartModule(), jpaPersistModule, new ValidationModule());

        List<Class<Managed>> managedServices = injector.getInstance(Key.get(new TypeLiteral<List<Class<Managed>>>()
        {
        }));

        System.out.println("managedServices: " + managedServices);

        for (Class<Managed> managedClass : managedServices)
        {
            injector.getInstance(managedClass).start();
            //register shutdown hook here
        }

//        HelloWorldService helloWorldService = injector.getInstance(HelloWorldService.class);
//        helloWorldService.createUser();
    }
}
