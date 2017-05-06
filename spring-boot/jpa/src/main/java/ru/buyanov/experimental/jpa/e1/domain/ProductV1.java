package ru.buyanov.experimental.jpa.e1.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author A.Buyanov 13.02.2016.
 */
@Entity
@Table(name = "product")
public class ProductV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToMany
    @JoinTable(name = "product_tag",
               joinColumns = @JoinColumn(name = "product_id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagV1> tags = new ArrayList<>();


    public ProductV1() {
    }

    public ProductV1(String name) {
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

    public List<TagV1> getTags() {
        return tags;
    }

    public void setTags(List<TagV1> tags) {
        this.tags = tags;
    }
}
