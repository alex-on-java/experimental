package ru.buyanov.experimental.jpa.e2.domain;

import javax.persistence.*;

/**
 * @author A.Buyanov 20.08.2016.
 */
@Entity
public class CategoryGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    Checklist checklist;

    @ManyToOne
    Category category;

    float grade;

    public CategoryGrade() {
    }

    public CategoryGrade(Checklist checklistId, Category category, float grade) {
        this.checklist = checklistId;
        this.category = category;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
