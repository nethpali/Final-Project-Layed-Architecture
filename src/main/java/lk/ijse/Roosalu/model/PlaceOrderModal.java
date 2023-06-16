package lk.ijse.Roosalu.model;

import javafx.scene.control.Alert;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.Order;
import lk.ijse.Roosalu.dto.ProductDto;

import java.sql.SQLException;

public class PlaceOrderModal {
    public static boolean placeOrder(Order order, ProductDto production){
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            if (OrderModel.save(order)){
                if (ProductionModel.update(production)){
                    System.out.println("reach");
                    DBConnection.getInstance().getConnection().commit();
                    return true;
                }else{
                    DBConnection.getInstance().getConnection().rollback();
                }
            }else{ }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {

            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }

        }
        return false;

    }
}
