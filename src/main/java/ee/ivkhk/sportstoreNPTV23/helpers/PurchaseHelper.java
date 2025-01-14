package ee.ivkhk.sportstoreNPTV23.helpers;

import ee.ivkhk.sportstoreNPTV23.entity.Purchase;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHelper {

    /**
     * Преобразует список покупок в строковое представление.
     *
     * @param purchases список объектов Purchase.
     * @return список строк с информацией о покупках.
     */
    public static List<String> formatPurchases(List<Purchase> purchases) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return purchases.stream()
                .map(purchase -> String.format("ID: %d, Клиент: %s %s, Продукт: %s, Количество: %d, Дата покупки: %s",
                        purchase.getId(),
                        purchase.getCustomer().getFirstName(),
                        purchase.getCustomer().getLastName(),
                        purchase.getProduct().getName(),
                        purchase.getQuantity(),
                        purchase.getPurchaseDate().format(formatter)))
                .collect(Collectors.toList());
    }
}
