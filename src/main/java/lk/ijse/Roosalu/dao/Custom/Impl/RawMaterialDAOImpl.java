package lk.ijse.Roosalu.dao.Custom.Impl;

import lk.ijse.Roosalu.Entity.Employee;
import lk.ijse.Roosalu.Entity.RawMaterial;
import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.dao.Custom.RawMaterialDAO;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialDAOImpl implements RawMaterialDAO {
    @Override
    public ArrayList<RawMaterial> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterial> allRawMaterial = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM RawMaterial");
        while (rst.next()) {
            RawMaterial rawMaterial=new RawMaterial(rst.getString("Raw_Material_Id"), rst.getString("Type"), rst.getInt("Quantity"),rst.getDouble("Unit_Price"),rst.getString("Agent_Id"));
            allRawMaterial.add(rawMaterial);
        }
        return allRawMaterial;
    }

    @Override
    public boolean add(RawMaterial dto) throws SQLException, ClassNotFoundException {
        return  CrudUtil.execute("INSERT INTO RawMaterial(Raw_Material_Id,Type,Quantity,Unit_Price,Agent_Id)VALUES(?,?,?,?,?)",dto.getRaw_material_id(),dto.getType(),dto.getQuantity(),dto.getUnit_price(),dto.getAgent_id());
    }

    @Override
    public boolean update(RawMaterial dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE RawMaterial SET type=?,Quantity=?,Unit_Price=?,Agent_Id=? WHERE Raw_Material_Id=?",
            dto.getType(),
            dto.getQuantity(),
            dto.getUnit_price(),
            dto.getAgent_id(),
            dto.getRaw_material_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE  FROM RawMaterial WHERE Raw_Material_Id= ?",id);
    }

    @Override
    public RawMaterial search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Rawmaterial WHERE Raw_Material_Id=?", id + "");
        if (rst.next()) {
            return new RawMaterial(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public List<String> loadAgentId() throws SQLException {
        ArrayList<String> allAgentId=new ArrayList<>();
        ResultSet rst=CrudUtil.execute("SELECT Agent_Id FROM Agent");
        while (rst.next()){
            String s = new String(rst.getString("Agent_Id"));
            allAgentId.add(s);
        }
        return  allAgentId;
    }

    @Override
    public List<String> loadRawMaterialType() {
        return null;
    }
}
