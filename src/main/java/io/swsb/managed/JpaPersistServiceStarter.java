package io.swsb.managed;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by swsb
 */
@Singleton
public class JpaPersistServiceStarter implements Managed
{
    @Inject
    private PersistService persistService;

    @Override
    public void start() throws Exception
    {
        persistService.start();
    }

    @Override
    public void stop() throws Exception
    {
        persistService.stop();
    }
}
