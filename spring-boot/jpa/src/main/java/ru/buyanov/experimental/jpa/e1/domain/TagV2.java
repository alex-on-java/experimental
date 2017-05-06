package ru.buyanov.experimental.jpa.e1.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author A.Buyanov 13.02.2016.
 */
@Entity
@Table(name = "tag")
public class TagV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Transient
    private List<ProductV2> products = new ArrayList<>();

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

    public List<ProductV2> getProducts() {
        return products;
    }

    public void setProducts(List<ProductV2> products) {
        this.products = products;
    }
}
