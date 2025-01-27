package ee.ivkhk.NPTV23Store.helpers;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.interfaces.CustomerHelper;
import ee.ivkhk.NPTV23Store.interfaces.Input;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerHelperImpl implements CustomerHelper {

    private final Input input;

    public CustomerHelperImpl(Input input) {
        this.input = input;
    }

    @Override
    public Optional<Customer> create() {
        try {
            System.out.print("Введите имя покупателя: ");
            String firstName = input.getString();

            System.out.print("Введите фамилию покупателя: ");
            String lastName = input.getString();

            System.out.print("Введите баланс покупателя: ");
            double balance = input.getDouble();

            if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || balance < 0) {
                System.out.println("Некорректные данные покупателя!");
                return Optional.empty();
            }

            Customer c = new Customer(firstName, lastName, balance);
            return Optional.of(c);
        } catch (Exception e) {
            System.out.println("Ошибка при создании покупателя: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> edit(Customer ignored) {
        try {
            System.out.print("Введите ID покупателя для редактирования: ");
            long id = input.getLong();

            Customer c = new Customer();
            c.setId(id);

            System.out.print("Введите новое имя (или оставьте пустым): ");
            String newFirst = input.getString();

            System.out.print("Введите новую фамилию (или оставьте пустым): ");
            String newLast = input.getString();

            System.out.print("Введите новый баланс (или -1 для пропуска): ");
            double newBalance = input.getDouble();

            if (!newFirst.isEmpty()) {
                c.setFirstName(newFirst);
            }
            if (!newLast.isEmpty()) {
                c.setLastName(newLast);
            }
            c.setBalance(newBalance);

            return Optional.of(c);
        } catch (Exception e) {
            System.out.println("Ошибка при редактировании покупателя: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean printList(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("Нет покупателей!");
            return false;
        }
        System.out.println("Список покупателей:");
        for (Customer c : customers) {
            System.out.printf("ID:%d | %s %s | Баланс: %.2f%n",
                    c.getId(), c.getFirstName(), c.getLastName(), c.getBalance());
        }
        return true;
    }

    @Override
    public Long findIdEntityForChangeAvailability(List<Customer> ts) {
        return 0L;
    }
}
