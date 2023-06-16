package lk.ijse.Roosalu.bo.Custom;

import lk.ijse.Roosalu.bo.SuperBo;
import lk.ijse.Roosalu.dto.EmployeeDto;
import lk.ijse.Roosalu.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBo extends SuperBo {
    public ArrayList<UserDto> getAllUser() throws SQLException, ClassNotFoundException;
    public boolean addUser(UserDto  dto) throws SQLException, ClassNotFoundException;
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException ;
    public UserDto searchUser(String id) throws SQLException, ClassNotFoundException;
}
