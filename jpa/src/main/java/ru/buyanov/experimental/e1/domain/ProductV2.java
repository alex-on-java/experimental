package ru.buyanov.experimental.e1.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author A.Buyanov 13.02.2016.
 */
@Entity
@Table(name = "product")
public class ProductV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Transient
    private List<TagV2> tags = new ArrayList<>();


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

    public List<TagV2> getTags() {
        return tags;
    }

    public void setTags(List<TagV2> tags) {
        this.tags = tags;
    }
}
