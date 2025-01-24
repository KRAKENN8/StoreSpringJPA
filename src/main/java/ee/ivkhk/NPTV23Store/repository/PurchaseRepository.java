package ee.ivkhk.NPTV23Store.repository;

import ee.ivkhk.NPTV23Store.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей Purchase в базе данных.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // Если нужны методы поиска по датам, добавьте:
    // List<Purchase> findByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);
}
