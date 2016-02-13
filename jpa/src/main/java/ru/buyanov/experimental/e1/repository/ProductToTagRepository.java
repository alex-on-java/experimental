package ru.buyanov.experimental.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.e1.domain.ProductToTag;
import ru.buyanov.experimental.e1.domain.ProductToTagPK;

import java.util.List;

/**
 * @author A.Buyanov 13.02.2016.
 */
public interface ProductToTagRepository extends JpaRepository<ProductToTag, ProductToTagPK> {
    List<ProductToTag> findAllByProductIdIn(List<Integer> idList);
}
