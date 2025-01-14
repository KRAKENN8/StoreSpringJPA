package ee.ivkhk.sportstoreNPTV23.services;

import ee.ivkhk.sportstoreNPTV23.entity.Customer;
import ee.ivkhk.sportstoreNPTV23.helpers.CustomerHelper;
import ee.ivkhk.sportstoreNPTV23.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Метод для добавления клиента
    public void addCustomer(String firstName, String lastName, double balance) {
        Customer customer = CustomerHelper.createCustomer(firstName, lastName, balance);
        customerRepository.save(customer);
    }

    // Метод для редактирования клиента
    public void editCustomer(Long id, String firstName, String lastName, Double balance) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Клиент с таким ID не найден."));
        if (firstName != null && !firstName.trim().isEmpty()) {
            customer.setFirstName(firstName.trim());
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            customer.setLastName(lastName.trim());
        }
        if (balance != null && balance >= 0) {
            customer.setBalance(balance);
        }
        customerRepository.save(customer);
    }

    // Метод для получения всех клиентов в читабельном формате
    public String getAllCustomersFormatted() {
        StringBuilder formattedCustomers = new StringBuilder();
        List<Customer> customers = customerRepository.findAll();
        List<String> formattedList = CustomerHelper.formatCustomers(customers);
        formattedList.forEach(s -> formattedCustomers.append(s).append("\n"));
        return formattedCustomers.toString();
    }

    // Метод для получения всех клиентов
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Метод для получения клиента по ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Клиент с таким ID не найден."));
    }

    // Метод для уменьшения баланса клиента
    public void decreaseCustomerBalance(Long id, double amount) {
        Customer customer = getCustomerById(id);
        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на балансе клиента.");
        }
        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
    }

    // Метод для увеличения баланса клиента
    public void increaseCustomerBalance(Long id, double amount) {
        Customer customer = getCustomerById(id);
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
    }
}
