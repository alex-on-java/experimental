package ru.buyanov.experimental.jpa.e2.domain;

import javax.persistence.*;

/**
 * @author A.Buyanov 20.08.2016.
 */
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String name;

    @ManyToOne
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    Template template;

    public Question() {
    }

    public Question(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Question(String name, Category category, Template template) {
        this.name = name;
        this.category = category;
        this.template = template;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
