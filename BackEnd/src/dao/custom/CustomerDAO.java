package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    JsonArray getCustomerId(Connection connection) throws SQLException, ClassNotFoundException;

}
