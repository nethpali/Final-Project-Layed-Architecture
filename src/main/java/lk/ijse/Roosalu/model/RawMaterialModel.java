package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.RawMaterialDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialModel {
    public static List<RawMaterialDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM rawmaterial";

        List<RawMaterialDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new RawMaterialDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }
        return data;

    }

    public static ArrayList<RawMaterialDto> View() throws SQLException {
        ArrayList<RawMaterialDto> rawMaterialArrayList= new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM rawmaterial");


        while (rst.next()) {
            rawMaterialArrayList.add(
                    new RawMaterialDto(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4),rst.getString(5))
            );
        }
        return rawMaterialArrayList;
    }

    public static RawMaterialDto search(String rawmaterialid) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM rawmaterial WHERE Raw_Material_Id='" + rawmaterialid+ "'");
        System.out.println(rst);
        if (rst.next()) {
            return new RawMaterialDto(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4),rst.getString(5));
        }
        return null;
    }
}

