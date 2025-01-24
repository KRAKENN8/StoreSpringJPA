package ee.ivkhk.NPTV23Store.input;

import ee.ivkhk.NPTV23Store.interfaces.Input;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getString() {
        return scanner.nextLine();
    }

    // При желании можно переопределить getInt, getDouble, getLong
    // но если default-реализация устраивает, их можно не трогать.

}
