package ee.ivkhk.sportstoreNPTV23.helpers;

import ee.ivkhk.sportstoreNPTV23.entity.Customer;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerHelper {

    /**
     * Преобразует список клиентов в строковое представление.
     *
     * @param customers список объектов Customer.
     * @return список строк с информацией о клиентах.
     */
    public static List<String> formatCustomers(List<Customer> customers) {
        return customers.stream()
                .map(customer -> String.format("ID: %d, Имя: %s %s, Баланс: %.2f",
                        customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getBalance()))
                .collect(Collectors.toList());
    }

    /**
     * Проверяет, является ли объект Customer валидным (имеет имя, фамилию и положительный баланс).
     *
     * @param customer объект Customer.
     * @return true, если объект валиден; false в противном случае.
     */
    public static boolean isValidCustomer(Customer customer) {
        return customer != null &&
                customer.getFirstName() != null && !customer.getFirstName().trim().isEmpty() &&
                customer.getLastName() != null && !customer.getLastName().trim().isEmpty() &&
                customer.getBalance() >= 0;
    }

    /**
     * Создает объект Customer на основе имени, фамилии и баланса.
     *
     * @param firstName имя клиента.
     * @param lastName фамилия клиента.
     * @param balance баланс клиента.
     * @return объект Customer.
     */
    public static Customer createCustomer(String firstName, String lastName, double balance) {
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя и фамилия клиента должны быть заполнены.");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Баланс клиента не может быть отрицательным.");
        }
        return new Customer(firstName, lastName, balance);
    }
}
