package ee.ivkhk.sportstoreNPTV23.interfaces;

import ee.ivkhk.sportstoreNPTV23.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Найти продукты по названию.
     *
     * @param name название продукта.
     * @return список продуктов с указанным названием.
     */
    List<Product> findByName(String name);
}
