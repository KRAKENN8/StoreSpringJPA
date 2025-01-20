package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.helpers.CustomerHelper;
import ee.ivkhk.NPTV23Store.interfaces.CustomerRepository;
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

    public void addCustomer(String firstName, String lastName, double balance) {
        Customer customer = CustomerHelper.createCustomer(firstName, lastName, balance);
        customerRepository.save(customer);
    }

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

    public String getAllCustomersFormatted() {
        StringBuilder formattedCustomers = new StringBuilder();
        List<Customer> customers = customerRepository.findAll();
        List<String> formattedList = CustomerHelper.formatCustomers(customers);
        formattedList.forEach(s -> formattedCustomers.append(s).append("\n"));
        return formattedCustomers.toString();
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Клиент с таким ID не найден."));
    }

    public void decreaseCustomerBalance(Long id, double amount) {
        Customer customer = getCustomerById(id);
        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на балансе клиента.");
        }
        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
    }

    public void increaseCustomerBalance(Long id, double amount) {
        Customer customer = getCustomerById(id);
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
    }
}
