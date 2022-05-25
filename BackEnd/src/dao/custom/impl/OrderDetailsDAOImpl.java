package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetail;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {


    @Override
    public boolean add(Connection connection, OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO `order detail` values (?,?,?,?)",orderDetail.getItemCode(),orderDetail.getOrderId(),orderDetail.getQty(),orderDetail.getPrice());
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Connection connection, OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
