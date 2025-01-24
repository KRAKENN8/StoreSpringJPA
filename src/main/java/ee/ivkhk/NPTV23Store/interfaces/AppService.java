package ee.ivkhk.NPTV23Store.interfaces;

public interface AppService<T> {
    boolean add();
    boolean update(T t);
    boolean changeAvailability();
    boolean print();
}
