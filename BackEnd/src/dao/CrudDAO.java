package dao;

import dao.SuperDAO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO {
    boolean add(Connection connection,T t) throws SQLException, ClassNotFoundException;

    boolean delete(Connection connection,ID id) throws SQLException, ClassNotFoundException;

    boolean update(Connection connection,T t) throws SQLException, ClassNotFoundException;

    T search(Connection connection,ID id) throws SQLException, ClassNotFoundException;

    JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException;


}

