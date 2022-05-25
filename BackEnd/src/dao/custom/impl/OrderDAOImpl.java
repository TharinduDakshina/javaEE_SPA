package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Connection connection, Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO `order` values(?,?,?,?,?)",order.getOrderId(),order.getcId(),order.getOrderDate(),order.getTime(),order.getCost());
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getOrderCount(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "Select count(*) from `order`");
        int countOfOrders=-1;
        if (resultSet.next()) {
            countOfOrders= resultSet.getInt(1);
        }
        return countOfOrders;
    }

    @Override
    public String getLastOrderId(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT `orderId` FROM `order` ORDER BY `orderId` DESC LIMIT 1");
        String orderId=null;
        if (resultSet.next()){
            orderId=resultSet.getString(1);
        }
        return orderId;
    }
}
