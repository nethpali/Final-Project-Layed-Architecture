package lk.ijse.Roosalu.bo.Custom.Impl;

import lk.ijse.Roosalu.bo.Custom.PlaceOrderBo;
import lk.ijse.Roosalu.dao.Custom.Impl.PlaceOrderDAOImpl;
import lk.ijse.Roosalu.dao.Custom.Impl.ProductDAOImpl;
import lk.ijse.Roosalu.dao.Custom.PlaceOrderDAO;
import lk.ijse.Roosalu.dao.Custom.ProductDAO;

public class PlaceOrderBoImpl implements PlaceOrderBo {
    PlaceOrderDAO placeOrderDAO = new PlaceOrderDAOImpl();
    ProductDAO productDAO = new ProductDAOImpl();

}
