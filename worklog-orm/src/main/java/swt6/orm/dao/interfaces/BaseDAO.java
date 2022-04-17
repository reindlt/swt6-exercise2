package swt6.orm.dao.interfaces;

import java.util.List;

public interface BaseDAO<T> {
    T insert(T entity);

    T update(T entity);

    void delete(T entity);

    T getById(long id);

    List<T> getAll();
}
