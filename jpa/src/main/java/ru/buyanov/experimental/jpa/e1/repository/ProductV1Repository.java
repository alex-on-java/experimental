package ru.buyanov.experimental.jpa.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buyanov.experimental.jpa.e1.domain.ProductV1;

/**
 * @author A.Buyanov 13.02.2016.
 */
public interface ProductV1Repository extends JpaRepository<ProductV1, Integer> {
}
