package ee.ivkhk.sportstoreNPTV23.interfaces;

import ee.ivkhk.sportstoreNPTV23.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /**
     * Найти покупки за определенный день.
     *
     * @param date начало дня.
     * @param dateEnd конец дня.
     * @return список покупок за день.
     */
    List<Purchase> findByPurchaseDateBetween(LocalDateTime date, LocalDateTime dateEnd);
}
