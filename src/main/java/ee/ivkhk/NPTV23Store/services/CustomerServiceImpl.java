package ee.ivkhk.NPTV23Store.services;

import ee.ivkhk.NPTV23Store.entity.Customer;
import ee.ivkhk.NPTV23Store.interfaces.CustomerService;
import ee.ivkhk.NPTV23Store.interfaces.CustomerHelper;
import ee.ivkhk.NPTV23Store.repository.CustomerRepository;
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
        try {
            // создаём покупателя через хелпер
            Optional<Customer> oc = customerHelper.create();
            if (oc.isEmpty()) {
                System.out.println("Ошибка: некорректные данные покупателя!");
                return false;
            }
            customerRepository.save(oc.get());
            System.out.println("Покупатель успешно добавлен!");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении покупателя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Customer ignored) {
        try {
            // редактируем покупателя через хелпер
            Optional<Customer> oc = customerHelper.edit(null);
            if (oc.isEmpty()) {
                System.out.println("Ошибка: неверные данные при редактировании покупателя!");
                return false;
            }
            Customer edited = oc.get();

            Optional<Customer> dbCustomer = customerRepository.findById(edited.getId());
            if (dbCustomer.isEmpty()) {
                System.out.println("Нет покупателя с ID: " + edited.getId());
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
            System.out.println("Покупатель успешно обновлён!");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении покупателя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean print() {
        try {
            customerHelper.printList(customerRepository.findAll());
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка покупателей: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean changeAvailability() {
        return false;
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // «Тихое» сохранение (без опроса пользователя), напр. при покупках
    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
