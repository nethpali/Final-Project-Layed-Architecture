package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.Entity.Assests;
import lk.ijse.Roosalu.Entity.Employee;
import lk.ijse.Roosalu.bo.Custom.EmployeeBO;
import lk.ijse.Roosalu.dao.Custom.AssestsDAO;
import lk.ijse.Roosalu.dao.Custom.EmployeeDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.dto.AssestDto;
import lk.ijse.Roosalu.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBoImpl implements EmployeeBO {
    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> allEmployee= new ArrayList<>();
        ArrayList<Employee> all =employeeDAO.getAll();
        for ( Employee dto: all) {
            allEmployee.add(new EmployeeDto(dto.getEmployee_id(),dto.getEmployee_nic(),dto.getEmployee_name(), dto.getEmployee_address(),dto.getEmployee_contact_no(),dto.getEmployee_salary_per_hour()));
        }
        return allEmployee;
    }

    @Override
    public boolean addEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getEmployee_id(), dto.getEmployee_nic(), dto.getEmployee_name(), dto.getEmployee_address(),dto.getEmployee_contact_no(), dto.getEmployee_salary_per_hour()));
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getEmployee_id(), dto.getEmployee_nic(), dto.getEmployee_name(), dto.getEmployee_address(),dto.getEmployee_contact_no(), dto.getEmployee_salary_per_hour()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        return new EmployeeDto(employee.getEmployee_id(), employee.getEmployee_nic(), employee.getEmployee_name(), employee.getEmployee_address(), employee.getEmployee_contact_no(), employee.getEmployee_salary_per_hour());
    }
}
