package io.swsb;

import com.google.inject.persist.Transactional;
import io.swsb.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by swsb
 */
public class HelloWorldService
{
    @Inject
    private EntityManager em;

    public String sayHello()
    {
        return "hello";
    }


    @Transactional
    public User createUser()
    {
//        User user = new User();
//        user.setId(1L);
//        user.setName("sample");

        User dbUser = em.find(User.class, 1L);
        dbUser.setName(System.currentTimeMillis() + "- now");
        return dbUser;
    }
}
