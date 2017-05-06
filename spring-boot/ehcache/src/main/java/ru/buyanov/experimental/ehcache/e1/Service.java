package ru.buyanov.experimental.ehcache.e1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author A.Buyanov 23.02.2016.
 */
@Repository
@Transactional
public class Service {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    public String getRole(String id) {
        return roleRepository.findOne(id).toString();
    }

    public String getUserById(int id) {
        return userRepository.findOne(id).toString();
    }

    public String getUserByNameJoin(String name) {
        return userRepository.findOneByName(name).toString();
    }

    public String getUserByName(String name) {
        return userRepository.findByName(name).toString();
    }
}
