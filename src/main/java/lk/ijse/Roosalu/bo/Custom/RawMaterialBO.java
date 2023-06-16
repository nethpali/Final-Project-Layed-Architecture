package lk.ijse.Roosalu.bo.Custom;

import lk.ijse.Roosalu.bo.SuperBo;
import lk.ijse.Roosalu.dto.RawMaterialDto;
import lk.ijse.Roosalu.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RawMaterialBO extends SuperBo {
    public ArrayList<RawMaterialDto> getAllRawMaterial() throws SQLException, ClassNotFoundException;
    public boolean addRawMaterial(RawMaterialDto  dto) throws SQLException, ClassNotFoundException;
    public boolean updateRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteRawMaterial(String id) throws SQLException, ClassNotFoundException ;
    public RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException;

}
