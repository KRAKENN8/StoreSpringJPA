package ee.ivkhk.NPTV23Store.helpers;

import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.interfaces.ProductHelper;
import ee.ivkhk.NPTV23Store.interfaces.Input;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductHelperImpl implements ProductHelper {

    private final Input input;

    public ProductHelperImpl(Input input) {
        this.input = input;
    }

    @Override
    public Optional<Product> create() {
        try {
            System.out.print("Введите название товара: ");
            String name = input.getString();
            System.out.print("Введите цену товара: ");
            double price = input.getDouble();
            System.out.print("Введите количество товара: ");
            int qty = input.getInt();

            if (name.isEmpty() || price <= 0 || qty < 0) {
                System.out.println("Некорректные данные!");
                return Optional.empty();
            }
            Product p = new Product(name, price, qty);
            return Optional.of(p);
        } catch (Exception e) {
            System.out.println("Ошибка при создании товара: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> edit() {
        try {
            System.out.print("Введите ID товара для редактирования: ");
            Long id = input.getLong();

            Product p = new Product();
            p.setId(id);

            System.out.print("Новое название (пусто — пропуск): ");
            String n = input.getString();
            System.out.print("Новая цена (0 — пропуск): ");
            double pr = input.getDouble();
            System.out.print("Новое количество (-1 — пропуск): ");
            int q = input.getInt();

            if (!n.isEmpty()) p.setName(n);
            if (pr > 0) p.setPrice(pr);
            if (q >= 0) p.setQuantity(q);

            return Optional.of(p);
        } catch (Exception e) {
            System.out.println("Ошибка при редактировании товара: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean printList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("Список товаров пуст!");
            return false;
        }
        for (Product p : products) {
            System.out.printf("ID:%d | %s | Цена: %.2f | Кол-во: %d%n",
                    p.getId(), p.getName(), p.getPrice(), p.getQuantity()
            );
        }
        return true;
    }

    @Override
    public Long findIdEntityForChangeAvailability(List<Product> ts) {
        return 0L;
    }
}
