package ee.ivkhk.NPTV23Store.interfaces;

import java.util.List;

public interface AppHelper<T> {
    boolean isValid(T entity);
    List<String> format(List<T> entities);
    T create(Object... args);
}
