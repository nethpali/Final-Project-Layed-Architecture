package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.Entity.Employee;
import lk.ijse.Roosalu.Entity.User;
import lk.ijse.Roosalu.bo.Custom.UserBo;
import lk.ijse.Roosalu.dao.Custom.EmployeeDAO;
import lk.ijse.Roosalu.dao.Custom.UserDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.dto.EmployeeDto;
import lk.ijse.Roosalu.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBoImpl implements UserBo {

    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public ArrayList<UserDto> getAllUser() throws SQLException, ClassNotFoundException {
        ArrayList<UserDto> allUser= new ArrayList<>();
        ArrayList<User> all =userDAO.getAll();
        for ( User dto: all) {
            allUser.add(new UserDto(dto.getUser_id(),dto.getPassword(),dto.getRe_enter_password(),dto.getUser_name(),dto.getUser_email()));
        }
        return allUser;
    }

    @Override
    public boolean addUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getUser_id(),dto.getPassword(),dto.getRe_enter_password(), dto.getUser_name(),dto.getUser_email()));
    }

    @Override
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getUser_id(),dto.getPassword(),dto.getRe_enter_password(), dto.getUser_name(),dto.getUser_email()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public UserDto searchUser(String id) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(id);
        return new UserDto(user.getUser_id(), user.getPassword(), user.getRe_enter_password(),user.getUser_name(),user.getUser_email());
    }
}
