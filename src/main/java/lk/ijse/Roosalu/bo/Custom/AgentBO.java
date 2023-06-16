package lk.ijse.Roosalu.bo.Custom;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.bo.SuperBo;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AgentBO extends SuperBo {
    public ArrayList<AgentDto> getAllAgents() throws SQLException, ClassNotFoundException;
    public boolean addAgents(AgentDto  dto) throws SQLException, ClassNotFoundException;
    public boolean updateAgents(AgentDto dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteAgents(String id) throws SQLException, ClassNotFoundException ;
    public AgentDto searchAgent(String id) throws SQLException, ClassNotFoundException;
}
