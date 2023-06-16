package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.AgentDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgentDAOImpl implements AgentDAO {

    @Override
    public ArrayList<Agent> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Agent> allAgents = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Agent");
        while (rst.next()) {
            Agent agent= new Agent(rst.getString("Agent_Id"), rst.getString("Email"), rst.getString("Agent_Name"), rst.getString("Agent_Conatct_No"), rst.getString("Agent_Company"));
            allAgents.add(agent);
        }
        return allAgents;
    }

    @Override
    public boolean add(Agent dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Agent(Agent_Id,Email,Agent_Name,Agent_Conatct_No,Agent_Company) VALUES (?,?,?,?,?)",
                dto.getId(),dto.getEmail(),dto.getName(),dto.getContact_no(),dto.getCompany());
    }

    @Override
    public boolean update(Agent dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Agent SET Email =?,Agent_Name =?,Agent_Conatct_No=?, Agent_Company =? WHERE Agent_Id =?",
                dto.getEmail(),dto.getName(),dto.getContact_no(),dto.getCompany(),dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Agent WHERE Agent_Id=?", id);
    }

    @Override
    public Agent search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Agent WHERE Agent_Id=?", id+"");
        if(rst.next()){
            return  new Agent(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
