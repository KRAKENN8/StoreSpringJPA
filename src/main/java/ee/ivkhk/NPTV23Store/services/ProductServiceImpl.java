package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.interfaces.ProductService;
import ee.ivkhk.NPTV23Store.interfaces.ProductHelper;
import ee.ivkhk.NPTV23Store.repository.ProductRepository;
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
        try {
            Optional<Product> op = productHelper.create();
            if (op.isEmpty()) {
                System.out.println("Ошибка: некорректные данные товара!");
                return false;
            }
            productRepository.save(op.get());
            System.out.println("Товар успешно добавлен!");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update() {
        try {
            Optional<Product> op = productHelper.edit();
            if (op.isEmpty()) {
                System.out.println("Ошибка: неверные данные при редактировании товара!");
                return false;
            }
            Product edited = op.get();

            Optional<Product> dbProd = productRepository.findById(edited.getId());
            if (dbProd.isEmpty()) {
                System.out.println("Нет товара с ID: " + edited.getId());
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
            System.out.println("Товар успешно обновлён!");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении товара: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean print() {
        try {
            productHelper.printList(productRepository.findAll());
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка товаров: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean changeAvailability() {
        return false;
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
