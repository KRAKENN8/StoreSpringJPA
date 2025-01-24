package ee.ivkhk.NPTV23Store;

import ee.ivkhk.NPTV23Store.interfaces.CustomerService;
import ee.ivkhk.NPTV23Store.interfaces.ProductService;
import ee.ivkhk.NPTV23Store.interfaces.PurchaseService;
import ee.ivkhk.NPTV23Store.interfaces.Input;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NPTV23StoreApplication implements CommandLineRunner {

	private final Input input;
	private final CustomerService customerService;
	private final ProductService productService;
	private final PurchaseService purchaseService;

	public NPTV23StoreApplication(
			Input input,
			CustomerService customerService,
			ProductService productService,
			PurchaseService purchaseService
	) {
		this.input = input;
		this.customerService = customerService;
		this.productService = productService;
		this.purchaseService = purchaseService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NPTV23StoreApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("------ Магазин товаров для домашних животных ------");
		boolean repeat = true;
		while (repeat) {
			try {
				System.out.println("Список задач:");
				System.out.println("0. Выйти из программы");
				System.out.println("1. Добавить товар");
				System.out.println("2. Список товаров");
				System.out.println("3. Редактировать товар");
				System.out.println("4. Добавить покупателя");
				System.out.println("5. Список покупателей");
				System.out.println("6. Редактировать покупателя");
				System.out.println("7. Купить товар");
				System.out.println("8. Доход магазина за период");

				System.out.print("Введите номер задачи: ");
				int task = input.getInt();
				switch (task) {
					case 0 -> repeat = false;
					case 1 -> {
						if (productService.add()) {
							System.out.println("Товар успешно добавлен!");
						} else {
							System.out.println("Ошибка при добавлении товара!");
						}
					}
					case 2 -> productService.print();
					case 3 -> {
						if (productService.update(null)) {
							System.out.println("Товар успешно обновлён!");
						} else {
							System.out.println("Ошибка при обновлении товара!");
						}
					}
					case 4 -> {
						if (customerService.add()) {
							System.out.println("Покупатель успешно добавлен!");
						} else {
							System.out.println("Ошибка при добавлении покупателя!");
						}
					}
					case 5 -> customerService.print();
					case 6 -> {
						if (customerService.update(null)) {
							System.out.println("Покупатель успешно обновлён!");
						} else {
							System.out.println("Ошибка при обновлении покупателя!");
						}
					}
					case 7 -> {
						if (purchaseService.add()) {
							System.out.println("Покупка успешно совершена!");
						} else {
							System.out.println("Ошибка при совершении покупки!");
						}
					}
					case 8 -> purchaseService.print(); // Здесь логика расчёта дохода
					default -> System.out.println("Выберите задачу из списка!");
				}
				System.out.println("----------------------------------------");
			} catch (Exception e) {
				System.out.println("Ошибка: " + e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("До свидания :)");
	}
}
