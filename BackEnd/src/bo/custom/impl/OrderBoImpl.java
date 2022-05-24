package bo.custom.impl;

import bo.custom.OrderBO;
import dto.OrderDTO;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderBoImpl implements OrderBO {
    @Override
    public JsonObject generateOrderId(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArray getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArray getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDTO searchOrder(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public JsonArray searchOrderDetails(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean placeOrder(Connection connection, OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return false;
    }
}
