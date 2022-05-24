package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DaoFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public class ItemBoImpl implements ItemBO {
    ItemDAO dao = (ItemDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOTypes.ITEM);
    @Override
    public JsonArray getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        return dao.getAll(connection);
    }

    @Override
    public JsonArray getAllItemsId(Connection connection) throws SQLException, ClassNotFoundException {
        return dao.getItemId(connection);
    }

    @Override
    public ItemDTO searchItem(Connection connection, String code) throws SQLException, ClassNotFoundException {
        Item search = dao.search(connection, code);
        if (search != null) {
            return new ItemDTO(search.getCode(), search.getDescription(), search.getQtyOnHand(), search.getUnitPrice());
        }
        return null;
    }

    @Override
    public boolean addItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getItemId(), itemDTO.getItemName(), itemDTO.getItemQty(), itemDTO.getItemPrice());
        return dao.add(connection,item);
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getItemId(), itemDTO.getItemName(), itemDTO.getItemQty(), itemDTO.getItemPrice());
        return dao.update(connection,item);
    }

    @Override
    public boolean updateQty(Connection connection, int i, String code) throws SQLException, ClassNotFoundException {
        return dao.updateQty(connection,i,code);
    }

    @Override
    public boolean deleteItem(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return dao.delete(connection,code);
    }
}
