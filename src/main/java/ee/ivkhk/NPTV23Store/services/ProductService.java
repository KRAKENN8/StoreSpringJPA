package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.helpers.ProductHelper;
import ee.ivkhk.NPTV23Store.interfaces.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(String name, double price, int quantity) {
        Product product = ProductHelper.createProduct(name, price, quantity);
        productRepository.save(product);
    }

    public void editProduct(Long id, String name, double price, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукт с таким ID не найден."));
        if (name != null && !name.trim().isEmpty()) {
            product.setName(name.trim());
        }
        if (price > 0) {
            product.setPrice(price);
        }
        if (quantity >= 0) {
            product.setQuantity(quantity);
        }
        productRepository.save(product);
    }

    public String getAllProductsFormatted() {
        StringBuilder formattedProducts = new StringBuilder();
        List<Product> products = productRepository.findAll();
        List<String> formattedList = ProductHelper.formatProducts(products);
        formattedList.forEach(s -> formattedProducts.append(s).append("\n"));
        return formattedProducts.toString();
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукт с таким ID не найден."));
    }

    public void decreaseProductQuantity(Long id, int quantity) {
        Product product = getProductById(id);
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Недостаточно товара на складе.");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
