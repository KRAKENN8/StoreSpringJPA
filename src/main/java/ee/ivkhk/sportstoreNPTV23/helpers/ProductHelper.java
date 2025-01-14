package ee.ivkhk.sportstoreNPTV23.helpers;

import ee.ivkhk.sportstoreNPTV23.entity.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductHelper {

    /**
     * Преобразует список продуктов в строковое представление.
     *
     * @param products список объектов Product.
     * @return список строк с информацией о продуктах.
     */
    public static List<String> formatProducts(List<Product> products) {
        return products.stream()
                .map(product -> String.format("ID: %d, Название: %s, Цена: %.2f, Количество: %d",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity()))
                .collect(Collectors.toList());
    }

    /**
     * Проверяет, является ли объект Product валидным (имеет название, цену и количество).
     *
     * @param product объект Product.
     * @return true, если объект валиден; false в противном случае.
     */
    public static boolean isValidProduct(Product product) {
        return product != null &&
                product.getName() != null && !product.getName().trim().isEmpty() &&
                product.getPrice() > 0 &&
                product.getQuantity() >= 0;
    }

    /**
     * Создает объект Product на основе названия, цены и количества.
     *
     * @param name название продукта.
     * @param price цена продукта.
     * @param quantity количество продукта.
     * @return объект Product.
     */
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
