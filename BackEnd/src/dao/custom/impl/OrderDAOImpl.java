package dao.custom.impl;

import dao.CrudDAO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderDAOImpl implements CrudDAO {
    @Override
    public boolean add(Connection connection, Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Object o) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Object search(Connection connection, Object o) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
