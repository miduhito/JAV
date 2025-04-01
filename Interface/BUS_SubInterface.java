package Interface;

import java.util.ArrayList;

public interface BUS_SubInterface<T> {
    ArrayList<T> getData();

    ArrayList<T> getDataById(String id);

    boolean add(T entity);

    boolean update(T entity);

    boolean delete(String id, String pair_id);

    boolean hide(String id, String pair_id);

    boolean regexInput(T entity);
}
