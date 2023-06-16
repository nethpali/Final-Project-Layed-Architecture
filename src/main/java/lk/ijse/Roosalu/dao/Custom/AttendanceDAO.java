package lk.ijse.Roosalu.dao.Custom;

import lk.ijse.Roosalu.Entity.Attendance;
import lk.ijse.Roosalu.dao.CrudDAO;

import java.util.List;

public interface AttendanceDAO extends CrudDAO<Attendance> {
    public List<String> loadEmployeeId();
}
