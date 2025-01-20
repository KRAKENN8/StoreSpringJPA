package ee.ivkhk.NPTV23Store.helpers;

import ee.ivkhk.NPTV23Store.entity.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductHelper {
    public static List<String> formatProducts(List<Product> products) {
        return products.stream()
                .map(product -> String.format("ID: %d, Название: %s, Цена: %.2f, Количество: %d",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity()))
                .collect(Collectors.toList());
    }
    public static boolean isValidProduct(Product product) {
        return product != null &&
                product.getName() != null && !product.getName().trim().isEmpty() &&
                product.getPrice() > 0 &&
                product.getQuantity() >= 0;
    }
    public static Product createProduct(String name, double price, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта должно быть заполнено.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть положительной.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество продукта не может быть отрицательным.");
        }
        return new Product(name, price, quantity);
    }
}
