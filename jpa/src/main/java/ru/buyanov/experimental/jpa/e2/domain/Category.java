package ru.buyanov.experimental.jpa.e2.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author A.Buyanov 20.08.2016.
 */
@Entity
@NamedEntityGraph(name = Category.PARENT, attributeNodes = @NamedAttributeNode("parent"))
public class Category {
    public static final String PARENT = "category.parent";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    Category parent;

    @OneToMany(mappedBy = "parent")
    Set<Category> children = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.SUBSELECT)
    Set<Question> questions = new LinkedHashSet<>();


    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
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

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
