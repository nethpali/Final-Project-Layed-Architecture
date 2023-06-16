package lk.ijse.Roosalu.model;

import lk.ijse.Roosalu.Util.CrudUtil;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.AgentDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentModel {
    public static List<AgentDto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM agent";

        List<AgentDto> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new AgentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static AgentDto search(String agentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM agent WHERE agent_Id='" + agentId + "'");
        System.out.println(rst);
        if (rst.next()) {
            return new AgentDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5));
        }
        return null;
    }


    public static ArrayList<AgentDto> View() throws SQLException {
        ArrayList<AgentDto> agentArrayList = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM agent");


        while (rst.next()) {
            agentArrayList.add(
                    new AgentDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5))
            );
        }
        return agentArrayList;
    }
}
