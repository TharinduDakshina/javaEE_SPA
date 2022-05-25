package dao.custom;

import dao.CrudDAO;
import entity.Order;

import javax.json.JsonArray;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order,String> {
    int getOrderCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastOrderId(Connection connection) throws SQLException, ClassNotFoundException;
}
