package io.swsb;

import com.google.inject.persist.Transactional;
import io.swsb.entity.User;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Created by swsb
 */
@Singleton
public class HelloWorldService
{
    @Inject
    private Provider<EntityManager> em;

    public String sayHello()
    {
        return "hello";
    }

    @Transactional
    public void createUser()
    {


        User user = new User();
        user.setId(1L);
        user.setName("sample");



        em.get().persist(user);
    }
}
