package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.bo.Custom.AgentBO;
import lk.ijse.Roosalu.dao.Custom.AgentDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class AgentBoImpl implements AgentBO {

    AgentDAO agentDAO = (AgentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AGENT);
    @Override
    public ArrayList<AgentDto> getAllAgents() throws SQLException, ClassNotFoundException {
        ArrayList<AgentDto> allAgents= new ArrayList<>();
        ArrayList<Agent> all = agentDAO.getAll();
        for (Agent dto : all) {
            allAgents.add(new AgentDto(dto.getId(), dto.getEmail(), dto.getName(),dto.getContact_no(),dto.getCompany()));
        }
        return allAgents;
    }

    @Override
    public boolean addAgents(AgentDto dto) throws SQLException, ClassNotFoundException {
        return agentDAO.add(new Agent(dto.getId(), dto.getEmail(), dto.getName(),dto.getContact_no(),dto.getCompany()));
    }

    @Override
    public boolean updateAgents(AgentDto dto) throws SQLException, ClassNotFoundException {
        return agentDAO.update(new Agent(dto.getId(), dto.getEmail(), dto.getName(),dto.getContact_no(),dto.getCompany()));
    }

    @Override
    public boolean deleteAgents(String id) throws SQLException, ClassNotFoundException {
        return agentDAO.delete(id);
    }

    @Override
    public AgentDto searchAgent(String id) throws SQLException, ClassNotFoundException {
      Agent agent=agentDAO.search(id);
      return  new AgentDto(agent.getId(),agent.getEmail(), agent.getName(), agent.getContact_no(),agent.getCompany());
    }
}
