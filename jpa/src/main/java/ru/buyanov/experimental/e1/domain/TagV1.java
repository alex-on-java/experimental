package ru.buyanov.experimental.e1.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author A.Buyanov 13.02.2016.
 */
@Entity
@Table(name = "tag")
public class TagV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;


    public TagV1() {
    }

    public TagV1(String name) {
        this.name = name;
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
}
