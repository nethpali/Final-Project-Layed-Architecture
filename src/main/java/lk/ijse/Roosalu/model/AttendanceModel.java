package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.AttendanceDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {
    public static List<AttendanceDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee_attendance";

        List<AttendanceDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new AttendanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static ArrayList<AttendanceDto> View() throws SQLException {
        ArrayList<AttendanceDto> attendsView = new ArrayList();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee_attendance");
        while (rst.next()) {
            attendsView.add(
                    new AttendanceDto(rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5)
                    )
            );
        }
        return attendsView;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT id FROM employee_attendance ORDER BY attendance_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] strings = currentOrderId.split("A0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O0" + id;
        }
        return "O001";
    }
}
