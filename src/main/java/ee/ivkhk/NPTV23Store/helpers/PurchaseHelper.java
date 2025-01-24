package ee.ivkhk.NPTV23Store.helpers;

import ee.ivkhk.NPTV23Store.entity.Purchase;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseHelper {
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
