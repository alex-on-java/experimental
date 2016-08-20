package ru.buyanov.experimental.jpa.e2.domain;

import javax.persistence.*;

/**
 * @author A.Buyanov  20.08.2016.
 */
@Entity
@NamedEntityGraph(name = Answer.QUESTION, attributeNodes = @NamedAttributeNode(value = "question", subgraph = "question.category")
        ,subgraphs = @NamedSubgraph(name = "question.category", attributeNodes = @NamedAttributeNode(value = "category")))
public class Answer {
    public static final String QUESTION = "answer.question";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    Checklist checklist;

    @Column(name = "checklist_id", insertable = false, updatable = false)
    Integer checklistId;

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

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
