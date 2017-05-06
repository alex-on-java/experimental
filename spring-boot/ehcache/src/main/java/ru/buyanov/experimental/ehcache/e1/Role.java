package ru.buyanov.experimental.ehcache.e1;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author A.Buyanov 06.02.2016.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Permission> permissions = new ArrayList<>();

    public Role() {
    }

    public Role(String id, Permission... permissions) {
        this.id = id;
        this.permissions = Arrays.asList(permissions);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
