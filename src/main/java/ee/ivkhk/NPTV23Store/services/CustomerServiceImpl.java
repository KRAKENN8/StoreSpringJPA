package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.repository.CustomerRepository;
import ee.ivkhk.NPTV23Store.interfaces.CustomerService;
import ee.ivkhk.NPTV23Store.interfaces.CustomerHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerHelper customerHelper;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerHelper customerHelper
    ) {
        this.customerRepository = customerRepository;
        this.customerHelper = customerHelper;
    }

    @Override
    public boolean add() {
        // Интерфейсный метод add() – интерактивное добавление через helper
        Optional<Customer> oc = customerHelper.create();
        if (oc.isEmpty()) return false;
        customerRepository.save(oc.get());
        return true;
    }

    @Override
    public boolean update(Customer ignored) {
        // Текущее интерактивное редактирование (через helper.edit(...))
        Optional<Customer> oc = customerHelper.edit(null);
        if (oc.isEmpty()) return false;

        Customer edited = oc.get();
        Optional<Customer> dbCustomer = findCustomerById(edited.getId());
        if (dbCustomer.isEmpty()) {
            System.out.println("Нет покупателя с таким ID: " + edited.getId());
            return false;
        }
        Customer existing = dbCustomer.get();

        if (edited.getFirstName() != null && !edited.getFirstName().isEmpty()) {
            existing.setFirstName(edited.getFirstName());
        }
        if (edited.getLastName() != null && !edited.getLastName().isEmpty()) {
            existing.setLastName(edited.getLastName());
        }
        if (edited.getBalance() >= 0) {
            existing.setBalance(edited.getBalance());
        }
        customerRepository.save(existing);
        return true;
    }

    @Override
    public boolean changeAvailability() {
        return false; // у Customer нет поля "available"
    }

    @Override
    public boolean print() {
        return customerHelper.printList(customerRepository.findAll());
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Наш новый метод: «тихое» сохранение без интерактива.
     */
    @Override
    public void saveCustomer(Customer c) {
        customerRepository.save(c);
    }
}
