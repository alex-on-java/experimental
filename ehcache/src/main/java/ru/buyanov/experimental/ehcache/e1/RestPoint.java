package ru.buyanov.experimental.ehcache.e1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author A.Buyanov 06.02.2016.
 * First of all, call e1/role to put Role with Permission into cache
 * Then if you call e1/user, the user will be loaded with join to Role, so only cache on Permission is hit
 * The same will be for e1/username, because of EntityGraph.EntityGraphType.LOAD
 * And for e1/usernameFetch there will be different select for Role and the cache will hit.
 */
@RestController
@RequestMapping("/e1")
public class RestPoint {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void create() {
        Role role = new Role("ROLE_VIEWER", Permission.VIEW);
        roleRepository.save(role);
        User user = new User("username", role);
        userRepository.save(user);
    }

    /**

     * @return
     */
    @RequestMapping("/role")
    public Role getRole() {
        return roleRepository.findOne("ROLE_VIEWER");
    }

    @RequestMapping("/user")
    public User getUserById() {
        return userRepository.findOne(1);
    }

    @RequestMapping("/username")
    public User getUserByName() {
        return userRepository.findOneByName("username");
    }

    @RequestMapping("/usernameFetch")
    public User getUserByNameFetch() {
        return userRepository.findByName("username");
    }
}
