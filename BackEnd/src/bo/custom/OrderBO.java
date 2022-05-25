package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    JsonObject generateOrderId(Connection connection) throws SQLException, ClassNotFoundException;

    JsonArray getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;

    JsonArray getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException;

    OrderDTO searchOrder(Connection connection, String id) throws SQLException, ClassNotFoundException;

    JsonArray searchOrderDetails(Connection connection,String id) throws SQLException, ClassNotFoundException;

    boolean placeOrder(Connection connection,OrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    int getOrderCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastOrder(Connection connection) throws SQLException, ClassNotFoundException;
}
