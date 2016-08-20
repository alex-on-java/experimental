package ru.buyanov.experimental.jpa.e2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.jpa.e2.domain.Category;

import java.util.List;

/**
 * @author A.Buyanov 20.08.2016.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findOneById(int id);
}
