package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.entity.Product;
import ee.ivkhk.NPTV23Store.entity.Purchase;
import ee.ivkhk.NPTV23Store.interfaces.*;
import ee.ivkhk.NPTV23Store.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseHelper purchaseHelper;
    private final CustomerService customerService;
    private final ProductService productService;
    private final Input input; // Нужно для считывания дат в print() (доход)

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
        this.input = input; // сохраняем Input
    }

    @Override
    public boolean add() {
        // Пункт 7: «Купить товар»
        Optional<Purchase> op = purchaseHelper.create();
        if (op.isEmpty()) {
            return false;
        }
        Purchase purchase = op.get();

        Long custId = purchase.getCustomer().getId();
        Long prodId = purchase.getProduct().getId();
        int qty = purchase.getQuantity();

        // Ищем покупателя
        Optional<Customer> oc = customerService.findCustomerById(custId);
        if (oc.isEmpty()) {
            System.out.println("Покупатель с ID " + custId + " не найден!");
            return false;
        }
        // Ищем товар
        Optional<Product> op2 = productService.findProductById(prodId);
        if (op2.isEmpty()) {
            System.out.println("Товар с ID " + prodId + " не найден!");
            return false;
        }

        Customer c = oc.get();
        Product p = op2.get();

        // Проверяем баланс и остаток товара
        double totalCost = p.getPrice() * qty;
        if (c.getBalance() < totalCost) {
            System.out.println("Недостаточно средств у покупателя!");
            return false;
        }
        if (p.getQuantity() < qty) {
            System.out.println("Недостаточно товара на складе!");
            return false;
        }

        // Сохраняем саму покупку (в таблицу purchase)
        purchase.setCustomer(c);
        purchase.setProduct(p);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchaseRepository.save(purchase);

        // «Тихо» обновляем данные (баланс, кол-во), не вызывая интерактивный update(...)
        c.setBalance(c.getBalance() - totalCost);
        p.setQuantity(p.getQuantity() - qty);

        // Сохраняем изменения через методы без опроса
        customerService.saveCustomer(c);
        productService.saveProduct(p);

        System.out.println("Покупка успешно совершена!");
        return true;
    }

    @Override
    public boolean update(Purchase purchase) {
        // Обычно покупку не редактируем
        return false;
    }

    @Override
    public boolean changeAvailability() {
        // Неактуально для покупок
        return false;
    }

    @Override
    public boolean print() {
        // Пункт 8: «Доход магазина за период» — дневной, месячный, годовой
        try {
            // 1) Доход за конкретный день
            System.out.print("Введите год (например, 2025): ");
            int year = input.getInt();
            System.out.print("Введите месяц (1-12): ");
            int month = input.getInt();
            System.out.print("Введите день (1-31): ");
            int day = input.getInt();

            LocalDate date = LocalDate.of(year, month, day);
            double dailyIncome = getIncomeRange(date, date);
            System.out.printf("Доход магазина за %s: %.2f%n", date, dailyIncome);

            // 2) Доход за месяц
            System.out.print("Введите год для расчета дохода за месяц: ");
            int yearM = input.getInt();
            System.out.print("Введите месяц (1-12): ");
            int monthM = input.getInt();
            LocalDate startOfMonth = LocalDate.of(yearM, monthM, 1);
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
            double monthlyIncome = getIncomeRange(startOfMonth, endOfMonth);
            System.out.printf("Доход магазина за %02d.%d: %.2f%n", monthM, yearM, monthlyIncome);

            // 3) Доход за год
            System.out.print("Введите год для расчета дохода: ");
            int fullYear = input.getInt();
            LocalDate startOfYear = LocalDate.of(fullYear, 1, 1);
            LocalDate endOfYear = LocalDate.of(fullYear, 12, 31);
            double yearlyIncome = getIncomeRange(startOfYear, endOfYear);
            System.out.printf("Доход магазина за год %d: %.2f%n", fullYear, yearlyIncome);

            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            return false;
        }
    }

    /**
     * Вспомогательный метод для расчёта дохода в интервале дат [start, end]
     */
    private double getIncomeRange(LocalDate start, LocalDate end) {
        LocalDateTime from = start.atStartOfDay();
        LocalDateTime to = end.plusDays(1).atStartOfDay();

        double totalIncome = 0.0;
        for (Purchase p : purchaseRepository.findAll()) {
            LocalDateTime pd = p.getPurchaseDate();
            if (!pd.isBefore(from) && pd.isBefore(to)) {
                totalIncome += p.getProduct().getPrice() * p.getQuantity();
            }
        }
        return totalIncome;
    }
}
