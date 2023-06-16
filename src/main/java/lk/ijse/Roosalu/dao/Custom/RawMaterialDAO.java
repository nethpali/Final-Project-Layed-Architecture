package lk.ijse.Roosalu.dao.Custom;

import lk.ijse.Roosalu.Entity.RawMaterial;
import lk.ijse.Roosalu.dao.CrudDAO;

import java.sql.SQLException;
import java.util.List;

public interface RawMaterialDAO extends CrudDAO<RawMaterial> {
    public List<String> loadAgentId () throws SQLException;
    public List<String> loadRawMaterialType();
}
