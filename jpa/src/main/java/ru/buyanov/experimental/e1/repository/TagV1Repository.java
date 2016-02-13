package ru.buyanov.experimental.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.e1.domain.TagV1;

/**
 * @author A.Buyanov 13.02.2016.
 */
public interface TagV1Repository extends JpaRepository<TagV1, Integer> {
}
