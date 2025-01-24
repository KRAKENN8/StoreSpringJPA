package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.entity.Purchase;
import ee.ivkhk.NPTV23Store.interfaces.*;
import ee.ivkhk.NPTV23Store.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseHelper purchaseHelper;
    private final CustomerService customerService;
    private final ProductService productService;
    private final Input input; // для логики дохода в print()

    public PurchaseServiceImpl(
            PurchaseRepository purchaseRepository,
            PurchaseHelper purchaseHelper,
            CustomerService customerService,
            ProductService productService,
            Input input
    ) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseHelper = purchaseHelper;
        this.customerService = customerService;
        this.productService = productService;
        this.input = input;
    }

    @Override
    public boolean add() {
        try {
            // «Купить товар»
            Optional<Purchase> op = purchaseHelper.create();
            if (op.isEmpty()) {
                System.out.println("Ошибка: неверные данные покупки!");
                return false;
            }
            Purchase purchase = op.get();

            Long custId = purchase.getCustomer().getId();
            Long prodId = purchase.getProduct().getId();
            int qty = purchase.getQuantity();

            Optional<Customer> oc = customerService.findCustomerById(custId);
            if (oc.isEmpty()) {
                System.out.println("Нет покупателя с ID " + custId);
                return false;
            }
            Optional<Product> op2 = productService.findProductById(prodId);
            if (op2.isEmpty()) {
                System.out.println("Нет товара с ID " + prodId);
                return false;
            }
            Customer c = oc.get();
            Product p = op2.get();

            double totalCost = p.getPrice() * qty;
            if (c.getBalance() < totalCost) {
                System.out.println("Недостаточно средств у покупателя!");
                return false;
            }
            if (p.getQuantity() < qty) {
                System.out.println("Недостаточно товара на складе!");
                return false;
            }

            // Сохраняем саму покупку
            purchase.setCustomer(c);
            purchase.setProduct(p);
            purchase.setPurchaseDate(LocalDateTime.now());
            purchaseRepository.save(purchase);

            // «Тихо» списываем баланс/остаток
            c.setBalance(c.getBalance() - totalCost);
            p.setQuantity(p.getQuantity() - qty);

            customerService.saveCustomer(c);
            productService.saveProduct(p);

            System.out.println("Покупка успешно совершена!");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при совершении покупки: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Purchase purchase) {
        // Обычно покупку не редактируем, но если нужно — аналогично
        return false;
    }

    @Override
    public boolean changeAvailability() {
        return false;
    }

    @Override
    public boolean print() {
        // Логика дохода (п.8)
        try {
            System.out.print("Введите год (например, 2025): ");
            int year = input.getInt();
            System.out.print("Введите месяц (1-12): ");
            int month = input.getInt();
            System.out.print("Введите день (1-31): ");
            int day = input.getInt();
            LocalDate date = LocalDate.of(year, month, day);
            double dailyIncome = getIncomeRange(date, date);
            System.out.printf("Доход магазина за %s: %.2f%n", date, dailyIncome);

            // Месяц
            System.out.print("Введите год для дохода за месяц: ");
            int yearM = input.getInt();
            System.out.print("Введите месяц (1-12): ");
            int monthM = input.getInt();
            LocalDate startM = LocalDate.of(yearM, monthM, 1);
            LocalDate endM = startM.withDayOfMonth(startM.lengthOfMonth());
            double monthlyIncome = getIncomeRange(startM, endM);
            System.out.printf("Доход магазина за %d-%02d: %.2f%n", yearM, monthM, monthlyIncome);

            // Год
            System.out.print("Введите год для дохода за год: ");
            int fullY = input.getInt();
            LocalDate startY = LocalDate.of(fullY, 1, 1);
            LocalDate endY = LocalDate.of(fullY, 12, 31);
            double yearlyIncome = getIncomeRange(startY, endY);
            System.out.printf("Доход магазина за год %d: %.2f%n", fullY, yearlyIncome);

            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при расчёте дохода: " + e.getMessage());
            return false;
        }
    }

    private double getIncomeRange(LocalDate start, LocalDate end) {
        var from = start.atStartOfDay();
        var to = end.plusDays(1).atStartOfDay();

        double total = 0.0;
        for (Purchase p : purchaseRepository.findAll()) {
            var pd = p.getPurchaseDate();
            if (!pd.isBefore(from) && pd.isBefore(to)) {
                total += p.getProduct().getPrice() * p.getQuantity();
            }
        }
        return total;
    }
}
