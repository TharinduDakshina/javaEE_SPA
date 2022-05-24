package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    JsonArray getAllItems(Connection connection) throws SQLException, ClassNotFoundException;

    JsonArray getAllItemsId(Connection connection) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(Connection connection, String code) throws SQLException, ClassNotFoundException;

    boolean addItem(Connection connection,ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection,ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateQty(Connection connection,int i,String s) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection,String code) throws SQLException, ClassNotFoundException;
}
