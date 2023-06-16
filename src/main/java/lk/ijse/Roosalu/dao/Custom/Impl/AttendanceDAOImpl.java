package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.Assests;
import lk.ijse.Roosalu.Entity.Attendance;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.AttendanceDAO;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.AttendanceDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public ArrayList<Attendance> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Attendance> allAttendance = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee_attendance");
        while (rst.next()) {
            Attendance attendance= new Attendance(rst.getString("Employee_Id"), rst.getString("Attendance_Id"), rst.getString("Date"), rst.getString("Time"), rst.getString("absent_present"));
            allAttendance.add(attendance);
        }
        return allAttendance;

    }

    @Override
    public boolean add(Attendance dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO employee_attendance(Employee_Id,Attendance_Id,Date,Time,absent_present) VALUES (?,?,?,?,?)",dto.getEmployee_id(),dto.getAttendance_id(),dto.getDate(),dto.getTime(),dto.getAbsent_present());
    }

    @Override
    public boolean update(Attendance dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Attendance search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee_attendance WHERE Attendance_Id=?", id + "");
        if (rst.next()) {
            return new Attendance(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public List<String> loadEmployeeId() {
        return null;
    }
}
