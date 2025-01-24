package ee.ivkhk.NPTV23Store.interfaces;

import java.util.List;
import java.util.Optional;

public interface AppHelper<T> {
    /**
     * Создаёт объект T (через ввод с консоли), возвращает Optional<T>.
     */
    Optional<T> create();

    /**
     * Редактирует объект T (через ввод с консоли).
     */
    Optional<T> edit(T t);

    /**
     * Печатает список объектов T.
     */
    boolean printList(List<T> ts);

    /**
     * Находит ID сущности, у которой нужно сменить доступность (или что-то аналогичное).
     */
    Long findIdEntityForChangeAvailability(List<T> ts);
}
