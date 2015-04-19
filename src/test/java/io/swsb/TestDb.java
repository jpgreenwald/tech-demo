package io.swsb;

import io.swsb.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import java.io.IOException;

/**
 * Created by swsb
 */
public class TestDb extends TestBootstrap
{
    @Test
    public void testSaveFind() throws IOException
    {
        EntityManager em = getInstance(EntityManager.class);
        User dbUser = em.find(User.class, 1L);
        System.out.println(dbUser);

        System.out.println("mark 1");
        HelloWorldService service = getInstance(HelloWorldService.class);
        User user = service.createUser();
        Assert.assertNotNull(user);

        System.out.println(user);

        dbUser = em.find(User.class, 1L);
        System.out.println(dbUser);

        System.out.println(new ObjectMapper().writeValueAsString(em.createNativeQuery("select * from user").getResultList()));

    }
}
