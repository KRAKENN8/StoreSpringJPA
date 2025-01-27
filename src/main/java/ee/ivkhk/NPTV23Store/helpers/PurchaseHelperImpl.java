package ee.ivkhk.NPTV23Store.helpers;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.entity.Purchase;
import ee.ivkhk.NPTV23Store.interfaces.PurchaseHelper;
import ee.ivkhk.NPTV23Store.interfaces.Input;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class PurchaseHelperImpl implements PurchaseHelper {

    private final Input input;

    public PurchaseHelperImpl(Input input) {
        this.input = input;
    }

    @Override
    public Optional<Purchase> create() {
        try {
            System.out.print("Введите ID покупателя: ");
            Long customerId = input.getLong();
            System.out.print("Введите ID товара: ");
            Long productId = input.getLong();
            System.out.print("Введите количество: ");
            int qty = input.getInt();

            Customer c = new Customer();
            c.setId(customerId);
            Product p = new Product();
            p.setId(productId);

            if (qty <= 0) {
                System.out.println("Количество должно быть положительным!");
                return Optional.empty();
            }

            Purchase purchase = new Purchase(c, p, qty, LocalDateTime.now());
            return Optional.of(purchase);
        } catch (Exception e) {
            System.out.println("Ошибка при создании покупки: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Purchase> edit(Purchase purchase) {
        return Optional.empty();
    }

    @Override
    public boolean printList(List<Purchase> purchases) {
        if (purchases == null || purchases.isEmpty()) {
            System.out.println("Список покупок пуст!");
            return false;
        }
        for (Purchase pur : purchases) {
            System.out.printf("ID:%d, ПокупательID:%d, ТоварID:%d, Количество:%d, Дата:%s%n",
                    pur.getId(),
                    pur.getCustomer().getId(),
                    pur.getProduct().getId(),
                    pur.getQuantity(),
                    pur.getPurchaseDate()
            );
        }
        return true;
    }

    @Override
    public Long findIdEntityForChangeAvailability(List<Purchase> ts) {
        return 0L;
    }
}
