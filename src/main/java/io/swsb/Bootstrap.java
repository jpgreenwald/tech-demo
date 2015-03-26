package io.swsb;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.swsb.managed.Managed;
import io.swsb.module.AutoStartModule;
import io.swsb.module.ExampleResourceModule;
import org.eclipse.persistence.config.PersistenceUnitProperties;

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
        properties.setProperty(PersistenceUnitProperties.LOGGING_LEVEL, "INFO");
        properties.setProperty(PersistenceUnitProperties.CACHE_SHARED_DEFAULT, "false");
        properties.setProperty(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        properties.setProperty("eclipselink.query-results-cache", "false");
        properties.setProperty("eclipselink.refresh", "true");
        jpaPersistModule.properties(properties);

        Injector injector = Guice.createInjector(new ExampleResourceModule(), new AutoStartModule(), jpaPersistModule);

        List<Class<Managed>> managedServices = injector.getInstance(Key.get(new TypeLiteral<List<Class<Managed>>>()
        {
        }));

        System.out.println("managedServices: " + managedServices);

        for (Class<Managed> managedClass : managedServices)
        {
            injector.getInstance(managedClass).start();
            //register shutdown hook here
        }

        HelloWorldService helloWorldService = injector.getInstance(HelloWorldService.class);
        helloWorldService.createUser();
    }
}
