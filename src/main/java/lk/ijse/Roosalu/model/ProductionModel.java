package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.ProductDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductionModel {

    public static boolean update(ProductDto production) throws SQLException {
        System.out.println(production.getQuantity());
        System.out.println(production.getProduction_id());
        boolean isUpdated = CrudUtil.execute("UPDATE product set Quantity=? where Product_Id=?",
                production.getQuantity(),
                production.getProduction_id()
        );

        return isUpdated;
    }

    public static List<ProductDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM product";

        List<ProductDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new ProductDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return data;
    }

    public static ProductDto search(String ProductId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM product WHERE product_Id='" + ProductId + "'");
        System.out.println(rst);
        if (rst.next()) {
            return new ProductDto(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4));
        }
        return null;
    }

    public static ArrayList<ProductDto> View() throws SQLException {
        ArrayList<ProductDto> productionArrayList = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM product");


        while (rst.next()) {
            productionArrayList.add(
                    new ProductDto(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4))
            );
        }
        return productionArrayList;
    }
    }
