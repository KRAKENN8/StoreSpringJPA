package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Purchase;
import ee.ivkhk.NPTV23Store.helpers.PurchaseHelper;
import ee.ivkhk.NPTV23Store.interfaces.PurchaseRepository;
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

    @Transactional
    public void purchaseProduct(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным.");
        }

        CustomerService cs = customerService;
        ProductService ps = productService;

        var customer = cs.getCustomerById(customerId);
        var product = ps.getProductById(productId);

        double totalPrice = product.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Недостаточно средств для покупки.");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Недостаточно товара на складе.");
        }

        cs.decreaseCustomerBalance(customerId, totalPrice);
        ps.decreaseProductQuantity(productId, quantity);

        Purchase purchase = new Purchase(customer, product, quantity, LocalDateTime.now());
        purchaseRepository.save(purchase);
    }

    public String getAllPurchasesFormatted() {
        StringBuilder formattedPurchases = new StringBuilder();
        List<Purchase> purchases = purchaseRepository.findAll();
        List<String> formattedList = PurchaseHelper.formatPurchases(purchases);
        formattedList.forEach(s -> formattedPurchases.append(s).append("\n"));
        return formattedPurchases.toString();
    }

    public double getIncome(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        List<Purchase> purchases = purchaseRepository.findByPurchaseDateBetween(startDateTime, endDateTime);
        return purchases.stream()
                .mapToDouble(purchase -> purchase.getProduct().getPrice() * purchase.getQuantity())
                .sum();
    }
}
