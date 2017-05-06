package ru.buyanov.experimental.jpa.e2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.buyanov.experimental.jpa.e2.domain.Category;

import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.*;

/**
 * @author A.Buyanov 20.08.2016.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c join fetch c.parent where c.id = ?1")
    Category fetchOneById(int id);

    @EntityGraph(value = Category.PARENT, type = EntityGraphType.LOAD)
    List<Category> findAllByNameContainsIgnoreCase(String namePart);

    List<Category> findAllByNameContainsIgnoreCaseAndIdNotNull(String namePart);
}
