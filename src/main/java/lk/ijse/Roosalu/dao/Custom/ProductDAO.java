package lk.ijse.Roosalu.dao.Custom;

import lk.ijse.Roosalu.Entity.Product;
import lk.ijse.Roosalu.dao.CrudDAO;
import lk.ijse.Roosalu.dto.OrderDto;

public interface ProductDAO extends CrudDAO<Product> {
    boolean saveOrder(OrderDto order);
}
