package ru.buyanov.experimental.jpa.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.jpa.e1.domain.TagV2;

import java.util.List;

/**
 * @author A.Buyanov 13.02.2016.
 */
public interface TagV2Repository extends JpaRepository<TagV2, Integer> {
    List<TagV2> findAllByIdIn(List<Integer> idList);
}
