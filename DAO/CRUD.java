package DAO;

import java.util.ArrayList;

public interface CRUD<T> {
    connectDatabase connDB = new connectDatabase();

    ArrayList<T> getData();

    boolean add(T entity);

    boolean update(T entity);

    boolean delete(String id);

    boolean hide(String id);

    boolean checkDuplicate(T entity, String Function);

    String generateID();
}
