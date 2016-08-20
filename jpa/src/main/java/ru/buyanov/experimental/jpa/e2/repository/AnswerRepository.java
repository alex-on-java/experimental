package ru.buyanov.experimental.jpa.e2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.jpa.e2.domain.Answer;

import java.util.List;

/**
 * @author A.Buyanov  20.08.2016.
 */
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @EntityGraph(value = Answer.QUESTION_WITH_CATEGORY_AND_TEMPLATE, type = EntityGraph.EntityGraphType.LOAD)
    List<Answer> findAllByChecklistId(int id);
}
