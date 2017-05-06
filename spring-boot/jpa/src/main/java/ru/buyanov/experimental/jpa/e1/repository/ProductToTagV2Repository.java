package ru.buyanov.experimental.jpa.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.buyanov.experimental.jpa.e1.domain.ProductToTagPK;
import ru.buyanov.experimental.jpa.e1.domain.ProductToTagV2;

import java.util.List;

/**
 * @author A.Buyanov 25.03.2017.
 */
public interface ProductToTagV2Repository extends JpaRepository<ProductToTagV2, ProductToTagPK> {
    @Query("select pt from ProductToTagV2 pt join fetch pt.tag")
    List<ProductToTagV2> fetchAll();
}
