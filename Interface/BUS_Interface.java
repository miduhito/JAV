package Interface;

import java.util.ArrayList;

public interface BUS_Interface<T>{

    ArrayList<T> getData();

    T getDataById(String id);

    boolean add(T entity);

    boolean update(T entity);

    boolean delete(String id);

    boolean hide(String id);

    boolean regexInput(T entity);

    String generateID();
}
