package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.OrderDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean save(OrderDto order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders " + "VALUE (?,?,?,?,?,?,?)");
        pstm.setString(1,order.getOrder_id());
        pstm.setString(2,order.getDate());
        pstm.setString(3,order.getAgent_id());
        pstm.setString(4,order.getProduct_id());
        pstm.setDouble(5,order.getUnit_price());
        pstm.setInt(6,order.getQuantity());
        pstm.setDouble(7,order.getTotal());

        return pstm.executeUpdate() > 0;
    }


    public static List<OrderDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders";

        List<OrderDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getDouble(7)

            ));
        }
        return data;
    }

    public static ArrayList<OrderDto> View() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDto> orderView = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM `orders`");

        while (rst.next()) {
            orderView.add(
                    new OrderDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getInt(6), rst.getDouble(7))
            );

        }
        return orderView;
    }

    public static OrderDto search(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orders WHERE order_id='" + orderId + "'");
        System.out.println(rst);
        if (rst.next()) {
            return new OrderDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getInt(6), rst.getDouble(7));
        }
        return null;
    }

    public static boolean update(OrderDto order) throws SQLException, ClassNotFoundException {
        boolean i = CrudUtil.execute("UPDATE orders set description=? where order_id=?",
                order.getOrder_id()
        );
        System.out.println(i);
        return i;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Order_Id FROM Orders ORDER BY Order_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] strings = currentOrderId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O0" + id;
        }
        return "O001";
    }
}
