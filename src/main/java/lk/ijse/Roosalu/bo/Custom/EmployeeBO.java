package lk.ijse.Roosalu.bo.Custom;

import lk.ijse.Roosalu.bo.SuperBo;
import lk.ijse.Roosalu.dto.AssestDto;
import lk.ijse.Roosalu.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBo {
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    public boolean addEmployee(EmployeeDto  dto) throws SQLException, ClassNotFoundException;
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException ;
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException;
}
