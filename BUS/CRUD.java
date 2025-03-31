package BUS;

import java.util.ArrayList;

public interface CRUD<T>{

    ArrayList<T> getData();

    T getDataById(String id);

    boolean add(T entity);

    boolean update(T entity);

    boolean delete(String id);

    boolean regexInput(T entity);

    String generateID();
}
