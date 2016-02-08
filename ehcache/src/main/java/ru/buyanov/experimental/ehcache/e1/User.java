package ru.buyanov.experimental.ehcache.e1;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author A.Buyanov 06.02.2016
 */
@Entity
@NamedEntityGraph(name = "user", attributeNodes = @NamedAttributeNode("role"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;


    /**
     * If we set LAZY, there could be problems with no Session, but Role could be cached
     * With EAGER Role is fetched by join and there is no chance to use cache
     * But it uses join on findOne(id) method. If we use findOneByName, then role is loaded in separate select
     * and cache is used for Role
     * So cache is useful on *ToMany relations, when you will definitely use separate select
     */
    @OneToOne
    private Role role;

    public User() {
    }

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
