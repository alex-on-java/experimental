package ru.buyanov.experimental.ehcache.e1;

import net.sf.ehcache.CacheManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.buyanov.experimental.ehcache.ApplicationEhcache;
import ru.buyanov.experimental.hibernate.query.HibernateSqlCounter;

import static org.junit.Assert.assertEquals;

/**
 * @author A.Buyanov 22.02.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationEhcache.class)
public class UserRolePermissionTest {
    public static final String ROLE_ID = "ROLE_VIEWER";
    public static final String USERNAME = "username";
    private Integer userId;

    @Autowired
    private Service service;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role role = new Role(ROLE_ID, Permission.VIEW);
        roleRepository.save(role);
        User user = new User(USERNAME, role);
        userRepository.save(user);
        userId = user.getId();
        clearCache();
        HibernateSqlCounter.startCount();
    }

    @Test
    public void testRoleIsCached() {
        //first have been selected
        roleRepository.findOne(ROLE_ID);
        assertEquals(1, HibernateSqlCounter.getCurrentResult());

        //second have been got from 2nd level cache
        roleRepository.findOne(ROLE_ID);
        assertEquals(1, HibernateSqlCounter.getCurrentResult());

        //clear 2nd level cache to be definitely sure that it was not 1st level cache
        clearCache();
        roleRepository.findOne(ROLE_ID);
        assertEquals(2, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testRoleAndPermissions() {
        //If we use roleRepository.findOne, we need @Transactional here to load lazily Permission
        //But setUp will be in this transaction also, so the first level cache will be used
        service.getRole(ROLE_ID);
        assertEquals(2, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testUserById() {
        //Loading User by id will force join on Role, so and Permission will be loaded separately
        service.getUserById(userId);
        assertEquals(2, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testUserByIdRoleInCache() {
        preloadRoleAndRestartCounter();
        //Loading User by id will force join on Role, so only cache on Permission is hit
        service.getUserById(userId);
        assertEquals(1, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testUserByNameJoinOnRole() {
        preloadRoleAndRestartCounter();
        //There is @EntityGraph on findOneByName method, with EntityGraphType == LOAD
        //This force to load Role with join, and only Permission will be got from cache
        clearCache(Role.class);
        service.getUserByNameJoin(USERNAME);
        assertEquals(1, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testUserByName() {
        preloadRoleAndRestartCounter();
        //Loading by property without @EntityGraph will force to use separate request to load Role
        //So Role and Permission will be in cache
        service.getUserByName(USERNAME);
        assertEquals(1, HibernateSqlCounter.getCurrentResult());

        //Let's check how many requests we need without cache on Role
        clearCache(Role.class);
        HibernateSqlCounter.startCount();
        service.getUserByName(USERNAME);
        assertEquals(2, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testRoleInCacheAfterUserByName() {
        service.getUserByName(USERNAME);
        HibernateSqlCounter.startCount();
        // Now we expect that Role is in cache
        service.getRole(ROLE_ID);
        assertEquals(0, HibernateSqlCounter.getCurrentResult());
    }

    @Test
    public void testRoleInCacheAfterUserByNameJoinOnRole() {
        service.getUserByNameJoin(USERNAME);
        HibernateSqlCounter.startCount();
        // Now we expect that Role is in cache
        service.getRole(ROLE_ID);
        assertEquals(0, HibernateSqlCounter.getCurrentResult());
    }

    private void preloadRoleAndRestartCounter() {
        //Loading Role with Permission to put them into 2nd level cache
        service.getRole(ROLE_ID);
        HibernateSqlCounter.startCount();
    }

    private void clearCache() {
        CacheManager.getInstance().clearAll();
    }

    private void clearCache(Class cls) {
        CacheManager.getInstance().getCache(cls.getName()).removeAll();
    }
}
