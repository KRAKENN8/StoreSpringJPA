package ee.ivkhk.sportstoreNPTV23.interfaces;

import ee.ivkhk.sportstoreNPTV23.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Найти клиентов по имени.
     *
     * @param firstName имя клиента.
     * @return список клиентов с указанным именем.
     */
    List<Customer> findByFirstName(String firstName);

    /**
     * Найти клиентов по фамилии.
     *
     * @param lastName фамилия клиента.
     * @return список клиентов с указанной фамилией.
     */
    List<Customer> findByLastName(String lastName);

    /**
     * Найти клиентов по имени и фамилии.
     *
     * @param firstName имя клиента.
     * @param lastName фамилия клиента.
     * @return список клиентов с указанными именем и фамилией.
     */
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
