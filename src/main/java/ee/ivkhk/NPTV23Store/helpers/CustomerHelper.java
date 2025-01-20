package ee.ivkhk.NPTV23Store.helpers;

import ee.ivkhk.NPTV23Store.entity.Customer;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerHelper {
    public static List<String> formatCustomers(List<Customer> customers) {
        return customers.stream()
                .map(customer -> String.format("ID: %d, Имя: %s %s, Баланс: %.2f",
                        customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getBalance()))
                .collect(Collectors.toList());
    }
    public static boolean isValidCustomer(Customer customer) {
        return customer != null &&
                customer.getFirstName() != null && !customer.getFirstName().trim().isEmpty() &&
                customer.getLastName() != null && !customer.getLastName().trim().isEmpty() &&
                customer.getBalance() >= 0;
    }
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
