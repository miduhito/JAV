package Interface;

import DAO.connectDatabase;

import java.util.ArrayList;

public interface DAO_SubInterface<T> {
    connectDatabase connDB = new connectDatabase();

    ArrayList<T> getData();

    ArrayList<T> getDataById(String id);

    T getDataById(String id, String pair_id);

    boolean add(T entity);

    boolean update(T entity);

    boolean delete(String id, String pair_id);

    boolean hide(String id, String pair_id);

    boolean checkDuplicate(T entity, String Function);
}
