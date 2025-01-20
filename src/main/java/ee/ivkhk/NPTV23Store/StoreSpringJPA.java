package ee.ivkhk.NPTV23Store;

import ee.ivkhk.NPTV23Store.interfaces.Input;
import ee.ivkhk.NPTV23Store.services.PurchaseService;
import ee.ivkhk.NPTV23Store.services.CustomerService;
import ee.ivkhk.NPTV23Store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class StoreSpringJPA implements CommandLineRunner {

	@Autowired
	private Input input;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PurchaseService purchaseService;

	public static void main(String[] args) {
		SpringApplication.run(StoreSpringJPA.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("------ Магазин для домашних животных ------");
		boolean repeat = true;
		while (repeat) {
			try {
				System.out.println("Список задач:");
				System.out.println("0. Выйти из программы");
				System.out.println("1. Добавить товар");
				System.out.println("2. Список товаров");
				System.out.println("3. Редактировать атрибуты товара");
				System.out.println("4. Добавить покупателя");
				System.out.println("5. Список покупателей");
				System.out.println("6. Редактировать атрибуты покупателя");
				System.out.println("7. Купить товар");
				System.out.println("8. Доход магазина за период");
				System.out.print("Введите номер задачи: ");
				int task = input.getInt();
				switch (task) {
					case 0:
						repeat = false;
						break;
					case 1:
						addProduct();
						break;
					case 2:
						showProducts();
						break;
					case 3:
						editProduct();
						break;
					case 4:
						addCustomer();
						break;
					case 5:
						showCustomers();
						break;
					case 6:
						editCustomer();
						break;
					case 7:
						purchaseProduct();
						break;
					case 8:
						showIncome();
						break;
					default:
						System.out.println("Выберите задачу из списка!");
				}
				System.out.println("----------------------------------------");
			} catch (Exception e) {
				System.out.println("Ошибка: " + e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("До свидания :)");
	}

	private void addProduct() {
		try {
			System.out.print("Введите название товара: ");
			String name = input.getString();
			System.out.print("Введите цену товара: ");
			double price = input.getDouble();
			System.out.print("Введите количество товара: ");
			int quantity = input.getInt();
			productService.addProduct(name, price, quantity);
			System.out.println("Товар успешно добавлен!");
		} catch (IllegalArgumentException e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void showProducts() {
		try {
			System.out.println("Список всех товаров:");
			System.out.println(productService.getAllProductsFormatted());
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void editProduct() {
		try {
			System.out.print("Введите ID товара для редактирования: ");
			Long productId = input.getLong();
			System.out.print("Введите новое название товара (или оставьте пустым): ");
			String name = input.getString();
			System.out.print("Введите новую цену товара (или введите 0 для пропуска): ");
			double price = input.getDouble();
			System.out.print("Введите новое количество товара (или введите -1 для пропуска): ");
			int quantity = input.getInt();
			productService.editProduct(productId, name.isEmpty() ? null : name, price, quantity);
			System.out.println("Товар успешно обновлен!");
		} catch (IllegalArgumentException e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void addCustomer() {
		try {
			System.out.print("Введите имя покупателя: ");
			String firstName = input.getString();
			System.out.print("Введите фамилию покупателя: ");
			String lastName = input.getString();
			System.out.print("Введите баланс покупателя: ");
			double balance = input.getDouble();
			customerService.addCustomer(firstName, lastName, balance);
			System.out.println("Покупатель успешно добавлен!");
		} catch (IllegalArgumentException e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void showCustomers() {
		try {
			System.out.println("Список всех покупателей:");
			System.out.println(customerService.getAllCustomersFormatted());
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void editCustomer() {
		try {
			System.out.print("Введите ID покупателя для редактирования: ");
			Long customerId = input.getLong();
			System.out.print("Введите новое имя покупателя (или оставьте пустым): ");
			String firstName = input.getString();
			System.out.print("Введите новую фамилию покупателя (или оставьте пустым): ");
			String lastName = input.getString();
			System.out.print("Введите новый баланс покупателя (или введите -1 для пропуска): ");
			double balanceInput = input.getDouble();
			Double balance = balanceInput >= 0 ? balanceInput : null;
			customerService.editCustomer(customerId, firstName.isEmpty() ? null : firstName,
					lastName.isEmpty() ? null : lastName, balance);
			System.out.println("Покупатель успешно обновлен!");
		} catch (IllegalArgumentException e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void purchaseProduct() {
		try {
			System.out.print("Введите ID покупателя: ");
			Long customerId = input.getLong();
			System.out.print("Введите ID товара: ");
			Long productId = input.getLong();
			System.out.print("Введите количество для покупки: ");
			int quantity = input.getInt();
			purchaseService.purchaseProduct(customerId, productId, quantity);
			System.out.println("Покупка успешно завершена!");
		} catch (IllegalArgumentException e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void showIncome() {
		try {
			System.out.print("Введите год (например, 2025): ");
			int year = input.getInt();
			System.out.print("Введите месяц (1-12): ");
			int month = input.getInt();
			System.out.print("Введите день (1-31): ");
			int day = input.getInt();
			LocalDate date = LocalDate.of(year, month, day);
			double dailyIncome = purchaseService.getIncome(date, date);
			System.out.printf("Доход магазина за %s: %.2f%n", date, dailyIncome);

			System.out.print("Введите месяц для расчета дохода (1-12): ");
			month = input.getInt();
			LocalDate startOfMonth = LocalDate.of(year, month, 1);
			LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
			double monthlyIncome = purchaseService.getIncome(startOfMonth, endOfMonth);
			System.out.printf("Доход магазина за месяц %d/%d: %.2f%n", month, year, monthlyIncome);

			System.out.print("Введите год для расчета дохода (например, 2025): ");
			year = input.getInt();
			LocalDate startOfYear = LocalDate.of(year, 1, 1);
			LocalDate endOfYear = LocalDate.of(year, 12, 31);
			double yearlyIncome = purchaseService.getIncome(startOfYear, endOfYear);
			System.out.printf("Доход магазина за год %d: %.2f%n", year, yearlyIncome);
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}
}
