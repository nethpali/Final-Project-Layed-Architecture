package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.PlaceOrder;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.OrderDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<PlaceOrder> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(PlaceOrder dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO orders(Order_Id,Date,Agent_Id,Product_Id,Unit_Price,Quantity,Total) VALUES (?,?,?,?,?,?,?)",dto.getOrder_id(),dto.getDate(),dto.getAgent_id(),dto.getProduct_id(),dto.getUnit_price(),dto.getQuantity(),dto.getTotal());
    }

    @Override
    public boolean update(PlaceOrder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public PlaceOrder search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst =CrudUtil.execute("SELECT * FROM `orders` WHERE Order_Id='"+id+"'");
        System.out.println(rst);
        if (rst.next()){
            return new PlaceOrder(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5),rst.getInt(6),rst.getDouble(7));
        }
        return null;
    }

    @Override
    public List<String> loadAgentId() throws SQLException {
        ArrayList<String> allAgentId=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT Agent_Id FROM Agent");
        while (rst.next()){
            String s = new String(rst.getString("Agent_Id"));
            allAgentId.add(s);
        }
        return  allAgentId;
    }

    @Override
    public List<String> loadProductId() throws SQLException {
        ArrayList<String> allProductId=new ArrayList<>();
        ResultSet rst=CrudUtil.execute("SELECT Product_Id FROM product");
        while (rst.next()){
            String s = new String(rst.getString("Product_Id"));
            allProductId.add(s);
        }
        return  allProductId;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT Order_Id FROM `orders` ORDER BY  Order_Id DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("Order_Id").replace("OID-", "")) + 1)) : "OID-001";
    }
}
