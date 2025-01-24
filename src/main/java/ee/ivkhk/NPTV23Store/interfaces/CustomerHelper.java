package ee.ivkhk.NPTV23Store.interfaces;

import ee.ivkhk.NPTV23Store.entity.Customer;

public interface CustomerHelper extends AppHelper<Customer> {
    // Никаких "createCustomer" или "formatCustomers" — они заменены методами из AppHelper<T>:
    //   Optional<Customer> create();
    //   Optional<Customer> edit(Customer t);
    //   boolean printList(List<Customer> list);
    //   Long findIdEntityForChangeAvailability(List<Customer> list);
}
