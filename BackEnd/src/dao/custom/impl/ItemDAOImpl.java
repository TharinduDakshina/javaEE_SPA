package dao.custom.impl;

import dao.CrudDAO;
import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean add(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO item VALUES (?,?,?,?)",item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());
    }

    @Override
    public boolean delete(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"Delete from item where code=?",code);
    }

    @Override
    public boolean update(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"UPDATE item SET description=?,qtyOnHand=?,unitPrice=? where code=?",item.getDescription(),item.getQtyOnHand(),item.getUnitPrice(),item.getCode());
    }

    @Override
    public Item search(Connection connection, String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "select * FROM item WHERE code=?",code);
        Item item=null;
        while (resultSet.next()) {
            item=new Item(resultSet.getString(1),resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4));
        }
        return item;
    }

    @Override
    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "select * FROM item");
        JsonArrayBuilder allItems = Json.createArrayBuilder();
        while (resultSet.next()) {
            JsonObjectBuilder itemObject = Json.createObjectBuilder();
            itemObject.add("itemId",resultSet.getString(1));
            itemObject.add("description",resultSet.getString(2));
            itemObject.add("qty",resultSet.getInt(3));
            itemObject.add("unitePrice",resultSet.getDouble(4));
            allItems.add(itemObject.build());
        }
        return allItems.build();
    }

    @Override
    public boolean updateQty(Connection connection, int qty, String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"update item set qtyOnHand=? where code=?",qty,code);
    }

    @Override
    public JsonArray getItemId(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT  code FROM item");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        while (resultSet.next()) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",resultSet.getString(1));
            arrayBuilder.add(objectBuilder.build());
        }
        return arrayBuilder.build();
    }
}
