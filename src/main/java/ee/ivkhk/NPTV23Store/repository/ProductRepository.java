package ee.ivkhk.NPTV23Store.repository;

import ee.ivkhk.NPTV23Store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
