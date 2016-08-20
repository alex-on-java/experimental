package ru.buyanov.experimental.jpa.e2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.jpa.e2.domain.Answer;

/**
 * @author A.Buyanov  20.08.2016.
 */
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
