package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.repository.ProductRepository;
import ee.ivkhk.NPTV23Store.interfaces.ProductService;
import ee.ivkhk.NPTV23Store.interfaces.ProductHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductHelper productHelper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductHelper productHelper
    ) {
        this.productRepository = productRepository;
        this.productHelper = productHelper;
    }

    @Override
    public boolean add() {
        // интерактивное добавление
        Optional<Product> op = productHelper.create();
        if (op.isEmpty()) return false;
        productRepository.save(op.get());
        return true;
    }

    @Override
    public boolean update(Product ignored) {
        // интерактивное редактирование
        Optional<Product> op = productHelper.edit(null);
        if (op.isEmpty()) return false;

        Product edited = op.get();
        Optional<Product> dbProd = findProductById(edited.getId());
        if (dbProd.isEmpty()) {
            System.out.println("Нет товара с таким ID: " + edited.getId());
            return false;
        }
        Product existing = dbProd.get();

        if (edited.getName() != null && !edited.getName().isBlank()) {
            existing.setName(edited.getName());
        }
        if (edited.getPrice() > 0) {
            existing.setPrice(edited.getPrice());
        }
        if (edited.getQuantity() >= 0) {
            existing.setQuantity(edited.getQuantity());
        }
        productRepository.save(existing);
        return true;
    }

    @Override
    public boolean changeAvailability() {
        return false; // если нет поля "available"
    }

    @Override
    public boolean print() {
        // список товаров
        return productHelper.printList(productRepository.findAll());
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Наш новый метод для «тихого» сохранения изменений товара
     */
    @Override
    public void saveProduct(Product p) {
        productRepository.save(p);
    }
}
