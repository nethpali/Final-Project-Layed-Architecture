package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.UserDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static List<UserDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";

        List<UserDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return data;
    }

    public static UserDto search(String userid) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM user WHERE user_Id='" + userid + "'");
        System.out.println(rst);
        if (rst.next()) {
            return new UserDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5));
        }
        return null;
    }

    public static ArrayList<UserDto> View() throws SQLException {
        ArrayList<UserDto> userArrayList = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM user");


        while (rst.next()) {
            userArrayList.add(
                    new UserDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5))
            );
        }
        return userArrayList;
    }
}
