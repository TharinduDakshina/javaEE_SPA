package dao.custom;

import dao.CrudDAO;
import entity.Item;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean updateQty(Connection connection, int qty, String code) throws SQLException, ClassNotFoundException;
    JsonArray getItemId(Connection connection) throws SQLException, ClassNotFoundException;
}
