package ee.ivkhk.NPTV23Store.input;

import ee.ivkhk.NPTV23Store.interfaces.Input;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in, "UTF-8");

    @Override
    public String getString() {
        return scanner.nextLine();
    }

    @Override
    public int getInt() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректное целое число: ");
            }
        }
    }

    @Override
    public double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректное число: ");
            }
        }
    }

    @Override
    public Long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректный ID (число): ");
            }
        }
    }
}
