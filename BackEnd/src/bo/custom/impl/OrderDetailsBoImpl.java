package bo.custom.impl;

import bo.custom.OrderDetailsBO;
import dao.DaoFactory;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dto.OrderDetailDTO;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDetailsBoImpl implements OrderDetailsBO {
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOTypes.ORDERDETAILS);
    @Override
    public boolean placeOrder(Connection connection, OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail = new OrderDetail(orderDetailDTO.getoId(), orderDetailDTO.getiId(), orderDetailDTO.getQty(), orderDetailDTO.getPrice());
        return orderDetailsDAO.add(connection,orderDetail);
    }
}
