package pl.mg.springwebsocket.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import pl.mg.springwebsocket.Profiles;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceJpaTestConfig.class})
@ActiveProfiles(value = {Profiles.TEST})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initializeDatabase() {
        userRepository.removeAll();
        userRepository.addUser(new UserEntity("1", "mac", "waw", new Date()));
        userRepository.addUser(new UserEntity("2", "mariusz", "lodz", new Date()));
        userRepository.addUser(new UserEntity("3", "mietek", "poznan", new Date()));
        userRepository.addUser(new UserEntity("4", "krzesimir", "gdansk", new Date()));
    }

    @Test
    public void getUsersList() {
        List<UserEntity> all = userRepository.getAll();
        assertEquals(4, all.size());
    }

    @Test
    public void updateUserTest() {
        // getUser
        UserEntity firstUser = userRepository.getByIdWithDetach("1");
        assertTrue(firstUser != null);
        assertEquals("mac", firstUser.getName());

        // updateUser
        String newName = "macccciej";
        firstUser.setName(newName);
        userRepository.updateUser(firstUser);

        // read him again
        UserEntity updated = userRepository.getByIdWithDetach("1");
        assertEquals(newName, updated.getName());

    }

    /**
     * This test proves that detached object can be easily overwritten, so objects should be updated directly after it obtain from the database or
     * never detached. Problem occurs when we are working in distributed platform
     */
    @Test
    public void testDoubleAccessWithDetachedObject() {
        // getUser with detach
        UserEntity firstUser = userRepository.getByIdWithDetach("1");
        assertTrue(firstUser != null);
        assertEquals("mac", firstUser.getName());
        // make some changes without save
        String newName = "macccciej";
        firstUser.setName(newName);

        // get the same user again in another object
        UserEntity secondUser = userRepository.getByIdWithDetach("1");
        assertTrue(secondUser != null);
        assertEquals("mac", secondUser.getName());

        // make some changes on the second object
        String newName2 = "kwadrat";
        secondUser.setName(newName2);
        userRepository.updateUser(secondUser);

        // update user in second object
        userRepository.updateUser(firstUser);

        // update user from first object
        UserEntity thirdUser = userRepository.getByIdWithDetach("1");
        assertEquals("macccciej", thirdUser.getName());

    }
    
    
    /**
     * Double access wthout detaching object -takie samo zachowanie
     */
    @Test
    public void testDoubleAccessWithoutDetaching() {
        // getUser with detach
        UserEntity firstUser = userRepository.getById("1");
        assertTrue(firstUser != null);
        assertEquals("mac", firstUser.getName());
        // make some changes without save
        String newName = "macccciej";
        firstUser.setName(newName);

        // get the same user again in another object
        UserEntity secondUser = userRepository.getById("1");
        assertTrue(secondUser != null);
        assertEquals("mac", secondUser.getName());

        // make some changes on the second object
        String newName2 = "kwadrat";
        secondUser.setName(newName2);
        userRepository.updateUser(secondUser);

        // update user in second object
        userRepository.updateUser(firstUser);

        // update user from first object
        UserEntity thirdUser = userRepository.getById("1");
        assertEquals("macccciej", thirdUser.getName());

    }
}
