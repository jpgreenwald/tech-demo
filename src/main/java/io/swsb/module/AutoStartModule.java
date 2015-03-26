package io.swsb.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import io.swsb.managed.ExampleGuiceJettyServer;
import io.swsb.managed.JpaPersistServiceStarter;
import io.swsb.managed.Managed;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swsb
 */
public class AutoStartModule extends AbstractModule
{

    private List<Class<Managed>> services = new ArrayList<>();

    @Override
    protected void configure()
    {
        //services that do not have an inject anywhere (like the managed services below need to be manually bound because they wont be found without classpath scanning otherwise
        bind(JpaPersistServiceStarter.class);
        bind(ExampleGuiceJettyServer.class);

        @SuppressWarnings("unchecked")
        TypeListener mangedListener = new TypeListener()
        {
            @Override
            public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter)
            {
                if (type.getRawType().getAnnotation(Singleton.class) == null)
                {
                    throw new RuntimeException(String.format("Managed service %s, must be annotated with javax.inject.Singleton", type.getRawType().getCanonicalName()));
                }
                services.add((Class<Managed>) type.getRawType());
            }
        };

        bindListener(new AbstractMatcher<TypeLiteral<?>>()
        {
            @Override
            public boolean matches(TypeLiteral<?> typeLiteral)
            {
                return Managed.class.isAssignableFrom(typeLiteral.getRawType());
            }
        }, mangedListener);

    }

    @Provides
    public List<Class<Managed>> provideListMangedServices()
    {
        System.out.println("services: " + services);
        return services;
    }
}
