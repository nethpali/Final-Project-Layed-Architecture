package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.Entity.Employee;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.EmployeeDAO;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("Employee_Id"), rst.getString("Employee_NIC"), rst.getString("Employee_Name"), rst.getString("Employee_Address"), rst.getString("Employee_Contact_No"), rst.getDouble("Employee_Salary_per_hour"));
            allEmployee.add(employee);
        }
        return allEmployee;
    }

    @Override
    public boolean add(Employee dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Employee(Employee_Id,Employee_NIC,Employee_Name,Employee_Address,Employee_Contact_No,Employee_Salary_per_hour) VALUES (?,?,?,?,?,?)", dto.getEmployee_id(), dto.getEmployee_nic(), dto.getEmployee_name(), dto.getEmployee_address(), dto.getEmployee_contact_no(), dto.getEmployee_salary_per_hour());
    }

    @Override
    public boolean update(Employee dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Employee SET Employee_NIC=?,Employee_Name=?,Employee_Address=?, Employee_Contact_No=?, Employee_Salary_per_hour=? WHERE Employee_Id=?", dto.getEmployee_nic(), dto.getEmployee_name(), dto.getEmployee_address(), dto.getEmployee_contact_no(), dto.getEmployee_salary_per_hour(),dto.getEmployee_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Employee WHERE Employee_Id=?", id);
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee WHERE Employee_Id=?", id + "");
        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            );
        }
        return null;
    }
}
