package ee.ivkhk.sportstoreNPTV23.services;

import ee.ivkhk.sportstoreNPTV23.entity.Purchase;
import ee.ivkhk.sportstoreNPTV23.helpers.PurchaseHelper;
import ee.ivkhk.sportstoreNPTV23.interfaces.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, CustomerService customerService, ProductService productService) {
        this.purchaseRepository = purchaseRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Метод для покупки продукта клиентом.
     *
     * @param customerId ID клиента.
     * @param productId  ID продукта.
     * @param quantity   количество продукта.
     */
    @Transactional
    public void purchaseProduct(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным.");
        }

        CustomerService cs = customerService;
        ProductService ps = productService;

        // Получение клиента и продукта
        var customer = cs.getCustomerById(customerId);
        var product = ps.getProductById(productId);

        // Проверка баланса и количества продукта
        double totalPrice = product.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Недостаточно средств для покупки.");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Недостаточно товара на складе.");
        }

        // Обновление баланса и количества продукта
        cs.decreaseCustomerBalance(customerId, totalPrice);
        ps.decreaseProductQuantity(productId, quantity);

        // Создание записи о покупке
        Purchase purchase = new Purchase(customer, product, quantity, LocalDateTime.now());
        purchaseRepository.save(purchase);
    }

    /**
     * Метод для получения всех покупок в читабельном формате.
     *
     * @return строковое представление всех покупок.
     */
    public String getAllPurchasesFormatted() {
        StringBuilder formattedPurchases = new StringBuilder();
        List<Purchase> purchases = purchaseRepository.findAll();
        List<String> formattedList = PurchaseHelper.formatPurchases(purchases);
        formattedList.forEach(s -> formattedPurchases.append(s).append("\n"));
        return formattedPurchases.toString();
    }

    /**
     * Метод для получения дохода магазина за указанный период.
     *
     * @param start начало периода.
     * @param end   конец периода.
     * @return общий доход за период.
     */
    public double getIncome(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        List<Purchase> purchases = purchaseRepository.findByPurchaseDateBetween(startDateTime, endDateTime);
        return purchases.stream()
                .mapToDouble(purchase -> purchase.getProduct().getPrice() * purchase.getQuantity())
                .sum();
    }
}
