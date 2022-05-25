package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DaoFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dto.OrderDTO;
import entity.Order;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderBoImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOTypes.ORDER);
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
        Order order = new Order(orderDTO.getOrderId(), orderDTO.getCstId(), orderDTO.getDate(), orderDTO.getTime(), orderDTO.getSubTotal());
        return orderDAO.add(connection,order);
    }

    @Override
    public int getOrderCount(Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderCount(connection);
    }

    @Override
    public String getLastOrder(Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.getLastOrderId(connection);
    }
}
