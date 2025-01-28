package ee.ivkhk.NPTV23Store.interfaces;

public interface Input {
    String getString();

    default int getInt() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректное целое число: ");
            }
        }
    }

    default double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректное число (double): ");
            }
        }
    }

    default long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректный long: ");
            }
        }
    }
}
