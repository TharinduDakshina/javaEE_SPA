package bo.custom;

import bo.SuperBO;
import dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDetailsBO extends SuperBO {
    boolean placeOrder(Connection connection, OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException;
}
