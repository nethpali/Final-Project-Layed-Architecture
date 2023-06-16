package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static List<EmployeeDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee";

        List<EmployeeDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            ));
        }
        return data;
    }

    public static EmployeeDto search(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee WHERE Employee_Id='" + employeeId + "'");
        System.out.println(rst);
        if (rst.next()) {
            return new EmployeeDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6));
        }
        return null;
    }

    public static ArrayList<EmployeeDto> View() throws SQLException {
        ArrayList<EmployeeDto> employeeArrayList = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee");


        while (rst.next()) {
            employeeArrayList.add(
                    new EmployeeDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6))
            );
        }
        return employeeArrayList;
    }
}
