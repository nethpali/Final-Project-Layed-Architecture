package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.User;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.UserDAO;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> allUser = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM User");
        while (rst.next()) {
            User user=new User(rst.getString("User_Id"),rst.getString("New_Password"),rst.getString("Re_Enter_Password"),rst.getString("User_Name"),rst.getString("User_Email"));
            allUser.add(user);
        }
        return allUser;
    }

    @Override
    public boolean add(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO User(User_Id,New_Password,Re_Enter_Password,User_Name,User_Email) VALUES (?,?,?,?,?)",dto.getUser_id(),dto.getPassword(),dto.getRe_enter_password(),dto.getUser_name(),dto.getUser_email());
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE User SET  New_Password=?,Re_Enter_Password=?,User_Name=?, User_Email=? WHERE User_Id=?",dto.getPassword(),dto.getRe_enter_password(),dto.getUser_name(),dto.getUser_email(),dto.getUser_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User WHERE User_Id= ?", id);
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM User  WHERE User_Id=?", id + "");
        rst.next();
       return  new User(id + "",rst.getString("New_Password"),rst.getString("Re_Enter_Password"),rst.getString("User_Name"),rst.getString("User_Email"));
    }
}
