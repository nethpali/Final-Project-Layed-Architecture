package lk.ijse.Roosalu.bo.Custom;

import lk.ijse.Roosalu.Entity.Product;
import lk.ijse.Roosalu.bo.SuperBo;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.ProductDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBo {
    public ArrayList<ProductDto> getAllProduct() throws SQLException, ClassNotFoundException;
    public boolean addProduct(ProductDto dto) throws SQLException, ClassNotFoundException;
    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException ;
    public boolean deleteProduct(String id) throws SQLException, ClassNotFoundException ;
    public ProductDto searchProduct(String id) throws SQLException, ClassNotFoundException;
}
