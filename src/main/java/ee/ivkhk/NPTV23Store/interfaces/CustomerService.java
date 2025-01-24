package ee.ivkhk.NPTV23Store.interfaces;

import ee.ivkhk.NPTV23Store.entity.Customer;

import java.util.Optional;

public interface CustomerService extends AppService<Customer> {

    // Если уже было:
    Optional<Customer> findCustomerById(Long id);

    /**
     * Сохраняет изменения покупателя напрямую, без вызова хелпера (редактирования).
     */
    void saveCustomer(Customer c);
}
