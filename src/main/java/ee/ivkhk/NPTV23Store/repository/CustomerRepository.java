package ee.ivkhk.NPTV23Store.repository;

import ee.ivkhk.NPTV23Store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей Customer в базе данных.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Здесь можно объявлять дополнительные методы поиска, если нужно
    // Например: List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
