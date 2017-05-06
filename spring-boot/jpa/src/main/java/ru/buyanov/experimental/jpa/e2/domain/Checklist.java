package ru.buyanov.experimental.jpa.e2.domain;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author A.Buyanov  20.08.2016.
 */
@Entity
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    float grade;

    @ManyToOne
    Template template;

    @OneToMany(mappedBy = "checklist")
    Set<Answer> answers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "checklist")
    Set<CategoryGrade> categoryGrades = new LinkedHashSet<>();


    public Checklist() {
    }

    public Checklist(float grade, Template template) {
        this.grade = grade;
        this.template = template;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<CategoryGrade> getCategoryGrades() {
        return categoryGrades;
    }

    public void setCategoryGrades(Set<CategoryGrade> categoryGrades) {
        this.categoryGrades = categoryGrades;
    }
}
