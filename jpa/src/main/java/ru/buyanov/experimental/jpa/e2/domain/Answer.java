package ru.buyanov.experimental.jpa.e2.domain;

import javax.persistence.*;

/**
 * @author A.Buyanov  20.08.2016.
 */
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    Question question;

    @ManyToOne
    Checklist checklist;

    float grade;

    public Answer() {
    }

    public Answer(Question question, Checklist checklist, float grade) {
        this.question = question;
        this.checklist = checklist;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
