package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.Entity.Assests;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.AssestsDAO;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.AssestDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssestsDAOImpl implements AssestsDAO {
    @Override
    public ArrayList<Assests> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Assests> allAssest = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Assests");
        while (rst.next()) {
            Assests assests = new Assests(rst.getString("Assests_Id"), rst.getString("Assests_Name"), rst.getString("Description"), rst.getInt("Qunatity"), rst.getDouble("Value"));
            allAssest.add(assests);
        }
        return allAssest;
    }

    @Override
    public boolean add(Assests dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Assests(Assests_Id,Assests_Name,Description,Qunatity,Value) VALUES (?,?,?,?,?)", dto.getId(), dto.getName(), dto.getDescription(), dto.getQuantity(), dto.getValue());
    }

    @Override
    public boolean update(Assests dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Assests SET  Assests_Name=?,Description=?,Qunatity=?, Value =? WHERE  Assests_Id=?", dto.getName(), dto.getDescription(), dto.getQuantity(), dto.getValue(), dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Assests WHERE Assests_Id=?", id);
    }

    @Override
    public Assests search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Assests WHERE Assests_Id=?", id + "");
        if (rst.next()) {
            return new Assests(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
        }
        return null;
    }
}
