package ee.ivkhk.NPTV23Store.interfaces;

import ee.ivkhk.NPTV23Store.entity.Customer;

import java.util.Optional;

public interface CustomerService extends AppService<Customer> {
    Optional<Customer> findCustomerById(Long id);
    void saveCustomer(Customer c);
}
