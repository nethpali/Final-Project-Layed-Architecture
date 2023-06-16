package lk.ijse.Roosalu.dao.Custom;

import lk.ijse.Roosalu.Entity.PlaceOrder;
import lk.ijse.Roosalu.dao.CrudDAO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<PlaceOrder> {
    public List<String> loadAgentId () throws SQLException;
    public List<String> loadProductId() throws SQLException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
}
