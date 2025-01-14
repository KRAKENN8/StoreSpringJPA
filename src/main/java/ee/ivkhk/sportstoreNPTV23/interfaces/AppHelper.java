package ee.ivkhk.sportstoreNPTV23.interfaces;

import java.util.List;

public interface AppHelper<T> {

    /**
     * Проверяет, является ли объект валидным.
     *
     * @param entity объект для проверки.
     * @return true, если объект валиден; false, если нет.
     */
    boolean isValid(T entity);

    /**
     * Преобразует список объектов в их строковое представление.
     *
     * @param entities список объектов.
     * @return список строк.
     */
    List<String> format(List<T> entities);

    /**
     * Создаёт объект на основе данных.
     *
     * @param args данные для создания объекта.
     * @return созданный объект.
     */
    T create(Object... args);
}
