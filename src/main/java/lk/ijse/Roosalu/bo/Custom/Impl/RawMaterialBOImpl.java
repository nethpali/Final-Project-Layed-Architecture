package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.Entity.RawMaterial;
import lk.ijse.Roosalu.bo.Custom.RawMaterialBO;
import lk.ijse.Roosalu.dao.Custom.AgentDAO;
import lk.ijse.Roosalu.dao.Custom.RawMaterialDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.RawMaterialDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialBOImpl implements RawMaterialBO {


    RawMaterialDAO rawMaterialDAO = (RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RAWMATERIAL);
    @Override
    public ArrayList<RawMaterialDto> getAllRawMaterial() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterialDto> allRawMaterial= new ArrayList<>();
        ArrayList<RawMaterial> all = rawMaterialDAO.getAll();
        for (RawMaterial dto : all) {
            allRawMaterial.add(new RawMaterialDto(dto.getRaw_material_id(),dto.getType(),dto.getQuantity(),dto.getUnit_price(),dto.getAgent_id()));

        }
        return allRawMaterial;
    }

    @Override
    public boolean addRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.add(new RawMaterial(dto.getRaw_material_id(),dto.getType(),dto.getQuantity(),dto.getUnit_price(),dto.getAgent_id()));
    }

    @Override
    public boolean updateRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.update(new RawMaterial(dto.getRaw_material_id(),dto.getType(),dto.getQuantity(),dto.getUnit_price(),dto.getAgent_id()));
    }

    @Override
    public boolean deleteRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.delete(id);
    }

    @Override
    public RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException {
        RawMaterial rawMaterial=rawMaterialDAO.search(id);
        return  new RawMaterialDto(rawMaterial.getRaw_material_id(), rawMaterial.getType(), rawMaterial.getQuantity(), rawMaterial.getUnit_price(), rawMaterial.getAgent_id());
    }
}
