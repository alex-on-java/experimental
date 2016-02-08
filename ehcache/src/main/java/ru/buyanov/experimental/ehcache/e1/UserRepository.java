package ru.buyanov.experimental.ehcache.e1;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author A.Buyanov 06.02.2016.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    @EntityGraph(value = "user", type = EntityGraph.EntityGraphType.LOAD)
    User findOneByName(String name);

    User findByName(String name);
}
