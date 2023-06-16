package lk.ijse.Roosalu.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Roosalu.bo.BoFactory;
import lk.ijse.Roosalu.bo.Custom.AgentBO;
import lk.ijse.Roosalu.bo.Custom.RawMaterialBO;
import lk.ijse.Roosalu.dao.Custom.AgentDAO;
import lk.ijse.Roosalu.dao.Custom.RawMaterialDAO;
import lk.ijse.Roosalu.dao.DAOFactory;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.Order;
import lk.ijse.Roosalu.dto.RawMaterialDto;
import lk.ijse.Roosalu.dto.tm.AgentTM;
import lk.ijse.Roosalu.dto.tm.RawMaterialTM;
import lk.ijse.Roosalu.model.AgentModel;
import lk.ijse.Roosalu.model.RawMaterialModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RawMaterialManagmentController implements Initializable {

    public JFXTextField txtUnitPrice;

    public TableColumn colUnitPrice;
    public TableColumn colAgentId;
    public ComboBox cmbAgentId;
    @FXML
    private TextField txtSearchRawMaterail;

    @FXML
    private JFXTextField txtRawMaterialId;

    @FXML
    private JFXTextField txtRawMaterialQty;

    @FXML
    private TableView<RawMaterialTM> tblRawMaterial;

    @FXML
    private TableColumn<?, ?> colRawMaterialId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colRawMaterialType;

    @FXML
    private TableColumn<?, ?> colRawMaterialqty;

    @FXML
    private ComboBox<String> cmbRawMaterialType;
    @FXML
    private JFXTextField txtOrderId;
    @FXML
    private JFXTextField txtAgentId;

    @FXML
    private ComboBox<String> cmbOrderId;
    private ObservableList<RawMaterialTM> observableList;
    private String OrderId;
    public static ArrayList<Order> OrderArrayList= new ArrayList();
    private String agentId;
    public static ArrayList<AgentDto> AgentArrayList= new ArrayList();



   RawMaterialBO rawMaterialBO=(RawMaterialBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.RAWMATERIAL);
   AgentBO agentBO=(AgentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.AGENT);
   AgentDAO agentDAO=(AgentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AGENT);
   RawMaterialDAO rawMaterialDAO=(RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RAWMATERIAL);
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        ObservableList<String> list = FXCollections.observableArrayList("linen","cotton","Satin","Lace","Leather");
        cmbRawMaterialType.setItems(list);
        loadAgentIdComboBox();
    }
    private void getAll() throws ClassNotFoundException {
        tblRawMaterial.getItems().clear();
        try {
            ArrayList<RawMaterialDto> rawMaterialList= rawMaterialBO.getAllRawMaterial();

            for (RawMaterialDto rawMaterial : rawMaterialList) {
                tblRawMaterial.getItems().add(new RawMaterialTM(rawMaterial.getRaw_material_id(),rawMaterial.getType(),rawMaterial.getQuantity(), rawMaterial.getUnit_price(), rawMaterial.getAgent_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }
    private void setCellValueFactory() {
        colRawMaterialId.setCellValueFactory(new PropertyValueFactory<>("raw_material_id"));
        colRawMaterialType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRawMaterialqty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colAgentId.setCellValueFactory(new PropertyValueFactory<>("agent_id"));

    }

    @FXML
    void btnRawMaterialAddOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        RawMaterialDto rawMaterial = new RawMaterialDto(txtRawMaterialId.getText(),(String) cmbRawMaterialType.getValue(),Integer.parseInt(txtRawMaterialQty.getText()),Double.parseDouble(txtUnitPrice.getText()),(String) cmbAgentId.getValue());

        if(rawMaterialBO.addRawMaterial(rawMaterial)){
            new Alert(Alert.AlertType.CONFIRMATION,"RawMaterial Added", ButtonType.OK).show();
        } else{
            new Alert(Alert.AlertType.WARNING,"RawMaterial Not Saved", ButtonType.OK).show();
        }
        getAll();
        ClearAll();
    }

    @FXML
    void btnRawMaterialUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        RawMaterialDto rawMaterial = new RawMaterialDto(txtRawMaterialId.getText(), (String) cmbRawMaterialType.getValue(), Integer.parseInt(txtRawMaterialQty.getText()), Double.parseDouble(txtUnitPrice.getText()), (String) cmbAgentId.getValue());
        if(rawMaterialBO.updateRawMaterial(new RawMaterialDto(rawMaterial.getRaw_material_id(), rawMaterial.getType(),rawMaterial.getQuantity(),rawMaterial.getUnit_price(),rawMaterial.getRaw_material_id()))){
            new Alert(Alert.AlertType.CONFIRMATION,"RawMaterial Updated", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        ClearAll();

    }


    @FXML
    void btnRawMaterialDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(rawMaterialBO.deleteRawMaterial(txtRawMaterialId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"RawMaterial Deleted", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        ClearAll();
    }

    @FXML
    void txtRawMaterialSearchOnAction(ActionEvent event) {
        String  id=txtSearchRawMaterail.getText();
        try{
            RawMaterialDto rawMaterialDto=rawMaterialBO.searchRawMaterial(id);
            if(rawMaterialDto!=null){
                fillData(rawMaterialDto);
            }else{
                new Alert(Alert.AlertType.WARNING,"RawMaterial Not Found").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        txtSearchRawMaterail.setText("");
    }

    private void fillData(RawMaterialDto rawMaterialDto) {
         txtRawMaterialId.setText(rawMaterialDto.getRaw_material_id());
         cmbRawMaterialType.setValue(rawMaterialDto.getType());
         txtRawMaterialQty.setText(String.valueOf(rawMaterialDto.getQuantity()));
         txtUnitPrice.setText(String.valueOf(rawMaterialDto.getUnit_price()));
         cmbAgentId.setValue(rawMaterialDto.getAgent_id());
    }

    public void loadAgentIdComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> list = FXCollections.observableArrayList();
        List<String> id =rawMaterialDAO.loadAgentId();
        for (String a : id) {
            list.add(a);
        }
       cmbAgentId.setItems(list);
    }
    public void cmbAgentIdOnAction(ActionEvent actionEvent) {
        agentId= (String) cmbAgentId.getValue();
        try {
            AgentDto agent= AgentModel.search(agentId);
            /*txtAgentName.setText(agent.getName());
            txtAgentCompany.setText(agent.getCompany());*/
            //txtAgentId.setText(agentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void ClearAll() {
       txtRawMaterialId.setText(null);
       cmbRawMaterialType.setValue(null);
       txtRawMaterialQty.setText(null);
       txtUnitPrice.setText(null);
       cmbAgentId.setValue(null);
    }
}
