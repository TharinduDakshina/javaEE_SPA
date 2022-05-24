package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT  * FROM customer");
        JsonArrayBuilder customerArray = Json.createArrayBuilder();
        while (rst.next()){
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",customer.getId());
            objectBuilder.add("name",customer.getName());
            objectBuilder.add("address",customer.getAddress());
            objectBuilder.add("salary",customer.getSalary());
            customerArray.add(objectBuilder.build());
        }
        return customerArray.build();
    }

    @Override
    public JsonArray getCustomerId(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT  id FROM customer");
        JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
        while (rst.next()) {
            String id = rst.getString(1);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",id);
            arrayBuilder2.add(objectBuilder.build());
        }
        return arrayBuilder2.build();
    }

    @Override
    public boolean add(Connection connection,Customer customer) throws SQLException, ClassNotFoundException {
        return  CrudUtil.executeUpdate(connection, "INSERT  into customer values(?,?,?,?)",customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
    }

    @Override
    public boolean delete(Connection connection,String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"Delete from Customer where id=?",id);
    }

    @Override
    public boolean update(Connection connection,Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"UPDATE Customer SET name=?,address=?,salary=? WHERE id=?",customer.getName(),customer.getAddress(),customer.getSalary(),customer.getId());
    }

    @Override
    public Customer search(Connection connection,String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "select * FROM customer WHERE id=?", id);
        Customer customer=null;
        while (rst.next()) {
            customer=new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4) );
        }
        return customer;
    }


}

