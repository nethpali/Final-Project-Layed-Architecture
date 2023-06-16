package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.Entity.Assests;
import lk.ijse.Roosalu.bo.Custom.AssestsBO;
import lk.ijse.Roosalu.dao.Custom.AgentDAO;
import lk.ijse.Roosalu.dao.Custom.AssestsDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.AssestDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class AssestsBoImpl implements AssestsBO {
    AssestsDAO assestsDAO = (AssestsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ASSESTS);

    @Override
    public ArrayList<AssestDto> getAllAssests() throws SQLException, ClassNotFoundException {
        ArrayList<AssestDto> allAssests = new ArrayList<>();
        ArrayList<Assests> all = assestsDAO.getAll();
        for (Assests dto : all) {
            allAssests.add(new AssestDto(dto.getId(), dto.getName(), dto.getDescription(), dto.getQuantity(), dto.getValue()));
        }
        return allAssests;
    }


    @Override
    public boolean addAssests(AssestDto dto) throws SQLException, ClassNotFoundException {
        return assestsDAO.add(new Assests(dto.getId(), dto.getName(), dto.getDescription(), dto.getQuantity(), dto.getValue()));
    }

    @Override
    public boolean updateAssests(AssestDto dto) throws SQLException, ClassNotFoundException {
        return assestsDAO.update(new Assests(dto.getId(), dto.getName(), dto.getDescription(), dto.getQuantity(), dto.getValue()));
    }

    @Override
    public boolean deleteAssests(String id) throws SQLException, ClassNotFoundException {
        return assestsDAO.delete(id);
    }

    @Override
    public AssestDto searchAssests(String id) throws SQLException, ClassNotFoundException {
        Assests assests = assestsDAO.search(id);
        return new AssestDto(assests.getId(), assests.getName(), assests.getDescription(), assests.getQuantity(), assests.getValue());
    }
}
