package bo.custom.impl;

import bo.custom.OrderDetailsBO;
import dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDetailsBoImpl implements OrderDetailsBO {
    @Override
    public boolean placeOrder(Connection connection, OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        return false;
    }
}
