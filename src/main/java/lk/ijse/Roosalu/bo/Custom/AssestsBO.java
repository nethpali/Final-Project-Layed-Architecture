package lk.ijse.Roosalu.bo.Custom;

import lk.ijse.Roosalu.bo.SuperBo;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.AssestDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AssestsBO extends SuperBo {
    public ArrayList<AssestDto> getAllAssests() throws SQLException, ClassNotFoundException;
    public boolean addAssests(AssestDto  dto) throws SQLException, ClassNotFoundException;
    public boolean updateAssests(AssestDto dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteAssests(String id) throws SQLException, ClassNotFoundException ;
    public AssestDto searchAssests(String id) throws SQLException, ClassNotFoundException;
}
