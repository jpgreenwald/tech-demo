package io.swsb.managed;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by swsb
 */
@Singleton
public class ExampleGuiceJettyServer implements Managed
{
    private GuiceResteasyBootstrapServletContextListener contextListener;
    private Server server;

    @Inject
    public ExampleGuiceJettyServer(GuiceResteasyBootstrapServletContextListener contextListener)
    {
        this.contextListener = contextListener;
    }

    @Override
    public void start() throws Exception
    {
        server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(HttpServletDispatcher.class);
        context.addEventListener(contextListener);
        context.addFilter(PersistFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
//        context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        context.addServlet(servletHolder, "/*");

        server.start();
    }

    @Override
    public void stop() throws Exception
    {
        server.stop();
    }
}
