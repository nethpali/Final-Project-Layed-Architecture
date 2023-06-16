package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.Employee;
import lk.ijse.Roosalu.Entity.Product;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.ProductDAO;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Product> allProduct = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Product");
        while (rst.next()) {
            Product product = new Product(rst.getString("Product_Id"), rst.getString("Date"), rst.getInt("Quantity"), rst.getDouble("Unit_Price"));
            allProduct.add(product);
        }
        return allProduct;
    }

    @Override
    public boolean add(Product dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Product(Product_Id,Date,Quantity,Unit_Price) VALUES (?,?,?,?)", dto.getProduction_id(), dto.getDate(), dto.getQuantity(), dto.getUnit_price());
    }

    @Override
    public boolean update(Product dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Product SET  Date=?,Quantity=?,Unit_Price=? WHERE Product_Id=?", dto.getDate(), dto.getQuantity(), dto.getUnit_price(), dto.getProduction_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Product WHERE Product_Id=?", id);
    }

    @Override
    public Product search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Product WHERE Product_Id=?", id + "");
        if (rst.next()) {
            return new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }
}
