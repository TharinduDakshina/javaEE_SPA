package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DaoFactory;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOTypes.CUSTOMER);

    @Override
    public JsonArray getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.getAll(connection);
    }

    @Override
    public JsonArray getAllCustomersId(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerId(connection);
    }


    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException {
        Customer search = customerDAO.search(connection, id);
        if (search != null){
            return new CustomerDTO(search.getId(),search.getName(), search.getAddress(), search.getSalary());
        }
        return null;
    }


    @Override
    public boolean addCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getCstId(), customerDTO.getCstName(), customerDTO.getCstAddress(), customerDTO.getCstSalary());
        return customerDAO.add(connection,customer);
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getCstId(), customerDTO.getCstName(), customerDTO.getCstAddress(), customerDTO.getCstSalary());
        return customerDAO.update(connection,customer);
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection,id);
    }
}
