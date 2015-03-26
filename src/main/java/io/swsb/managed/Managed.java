package io.swsb.managed;

import javax.inject.Singleton;

/**
 * Created by swsb
 */
public interface Managed
{
    public void start() throws Exception;

    public void stop() throws Exception;
}
