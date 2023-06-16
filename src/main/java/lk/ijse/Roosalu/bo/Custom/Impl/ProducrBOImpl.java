package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.Entity.Employee;
import lk.ijse.Roosalu.Entity.Product;
import lk.ijse.Roosalu.bo.Custom.ProductBO;
import lk.ijse.Roosalu.dao.Custom.EmployeeDAO;
import lk.ijse.Roosalu.dao.Custom.ProductDAO;
import lk.ijse.Roosalu.dao.Custom.UserDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.dto.EmployeeDto;
import lk.ijse.Roosalu.dto.ProductDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProducrBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

    @Override
    public ArrayList<ProductDto> getAllProduct() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDto> allProduct = new ArrayList<>();
        ArrayList<Product> all = productDAO.getAll();
        for (Product dto : all) {
            allProduct.add(new ProductDto(dto.getProduction_id(), dto.getDate(), dto.getQuantity(), dto.getUnit_price()));
        }
        return allProduct;
    }

    @Override
    public boolean addProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDAO.add(new Product(dto.getProduction_id(), dto.getDate(), dto.getQuantity(), dto.getUnit_price()));
    }

    @Override
    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDAO.update(new Product(dto.getProduction_id(), dto.getDate(), dto.getQuantity(), dto.getUnit_price()));
    }

    @Override
    public boolean deleteProduct(String id) throws SQLException, ClassNotFoundException {
        return productDAO.delete(id);
    }

    @Override
    public ProductDto searchProduct(String id) throws SQLException, ClassNotFoundException {
        Product product = productDAO.search(id);
        return new ProductDto(product.getProduction_id(), product.getDate(), product.getQuantity(), product.getUnit_price());
    }
}


