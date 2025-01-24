package ee.ivkhk.NPTV23Store.interfaces;

import ee.ivkhk.NPTV23Store.entity.Product;

import java.util.Optional;

public interface ProductService extends AppService<Product> {

    Optional<Product> findProductById(Long id);

    /**
     * Сохраняет изменения товара напрямую, без вызова хелпера.
     */
    void saveProduct(Product p);
}
