package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    JsonArray getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;
    JsonArray getAllCustomersId(Connection connection) throws SQLException, ClassNotFoundException;


    CustomerDTO searchCustomer(Connection connection , String id) throws SQLException, ClassNotFoundException;


    boolean addCustomer(Connection connection,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Connection connection,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(Connection connection,String id) throws SQLException, ClassNotFoundException;
}
