package ru.buyanov.experimental.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.e1.domain.ProductV2;

/**
 * @author A.Buyanov 13.02.2016.
 */
public interface ProductV2Repository extends JpaRepository<ProductV2, Integer> {
}
