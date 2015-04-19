package io.swsb.managed;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by swsb
 */

public class JpaPersistServiceStarter
{
    @Inject
    private PersistService persistService;


    public void start() throws Exception
    {
        persistService.start();
    }


    public void stop() throws Exception
    {
        persistService.stop();
    }
}
