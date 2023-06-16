package lk.ijse.Roosalu.dao.Custom;

import lk.ijse.Roosalu.dto.OrderDto;

public interface PlaceOrderDAO {

    boolean saveOrder(OrderDto order);
}
