package lk.ijse.Roosalu.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Roosalu.Util.Regex;
import lk.ijse.Roosalu.Util.TextFields;
import lk.ijse.Roosalu.bo.BoFactory;
import lk.ijse.Roosalu.bo.Custom.AssestsBO;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.AssestDto;
import lk.ijse.Roosalu.dto.tm.AssetsTM;
import lk.ijse.Roosalu.model.AssestModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.*;
//import java.util.Date;


public class AssestsManageController implements Initializable {

    AssestsBO assestsBO= (AssestsBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ASSESTS);
    private final static String URL = "jdbc:mysql://localhost:3306/roosalu";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public Label lblDate;
    public Label lblTime;
    public TextField txtSearch;

    @FXML
        private Button btnSave;

        @FXML
        private Button btnUpdate;

        @FXML
        private Button btndelete;

        @FXML
        private JFXTextField txtassestid;

        @FXML
        private JFXTextField txtassestqty;

        @FXML
        private JFXTextField txtassestdescription;

        @FXML
        private JFXTextField txtassestname;

        @FXML
        private JFXTextField txtassetvalue;

        @FXML
        private JFXTextField txtassestdate;

        @FXML
        private TableView<AssetsTM> tblAssest;

        @FXML
        private TableColumn<?, ?> colUserId;

        @FXML
        private TableColumn<?, ?> colAssestId;

        @FXML
        private TableColumn<?, ?> colAssetsName;

        @FXML
        private TableColumn<?, ?> colassestQuantity;

        @FXML
        private TableColumn<?, ?> colAssestDescription;

        @FXML
        private TableColumn<?, ?> colAssestValue;

        @FXML
        private TableColumn<?, ?> colAssestDate;
        @FXML
        private ComboBox<?> cmbUserId;

    private ObservableList<AssetsTM> observableList;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        clearAll();

        //lblDate.setText(String.valueOf(LocalDate.now()));
        //TimeNow();
    }

    void getAll() throws SQLException {
       tblAssest.getItems().clear();
        try {
            List<AssestDto> assestList = assestsBO.getAllAssests();

            for (AssestDto assest : assestList) {
                tblAssest.getItems().add(new AssetsTM(assest.getId(),assest.getName(),assest.getDescription(),assest.getQuantity(),assest.getValue()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }

    private void setCellValueFactory() {
        colAssestId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAssetsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAssestDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colassestQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAssestValue.setCellValueFactory(new PropertyValueFactory<>("value"));

    }

    @FXML
    void btnAssestSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       /* if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"").show();
            return;
        }*/
        AssestDto assest = new AssestDto(txtassestid.getText(),txtassestname.getText(),txtassestdescription.getText(),Integer.parseInt(txtassestqty.getText()),Double.parseDouble(txtassetvalue.getText()));
        if(assestsBO.addAssests(new AssestDto(assest.getId(), assest.getName(), assest.getDescription(),assest.getQuantity(),assest.getValue()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Assests Added", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Assests  Not Saved", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
        void btnAssesUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"").show();
            return;
        }*/
        AssestDto assest = new AssestDto(txtassestid.getText(),txtassestname.getText(),txtassestdescription.getText(),Integer.parseInt(txtassestqty.getText()),Double.parseDouble(txtassetvalue.getText()));
        if(assestsBO.updateAssests(new AssestDto(assest.getId(), assest.getName(), assest.getDescription(),assest.getQuantity(),assest.getValue()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Assest Updated", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
    void btnAssetDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(assestsBO.deleteAssests(txtassestid.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Assests Deleted", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }
    private void clearAll() {
        txtassestid.setText(null);
        txtassestname.setText(null);
        txtassestdescription.setText(null);
        txtassestqty.setText(null);
        txtassetvalue.setText(null);
    }

    public void btnAssestsSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String  id=txtSearch.getText();
        try{
            AssestDto assestDto=assestsBO.searchAssests(id);
            if(assestDto !=null){
                fillData(assestDto);
            }else{
                new Alert(Alert.AlertType.WARNING,"Assests Not Found").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        txtSearch.setText("");
    }
    public void fillData(AssestDto assestDto){
        txtassestid.setText(assestDto.getId());
        txtassestname.setText(assestDto.getName());
        txtassestdescription.setText(assestDto.getDescription());
        txtassestqty.setText(String.valueOf(assestDto.getQuantity()));
        txtassetvalue.setText(String.valueOf(assestDto.getValue()));
    }
    public void txtAssestId(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtassestid);
    }

    public void txtAssestqty(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtassestqty);
    }

    public void txtAssestDescription(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtassestdescription);
    }

    public void txtAssestName(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtassestname);
    }

    public void txtAssestValue(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtassetvalue);
    }
    public boolean isValidated(){
        if (!Regex.setTextColor(TextFields.INVOICE,txtassestname))return false;
        if (!Regex.setTextColor(TextFields.INTEGER,txtassestqty))return false;
        if (!Regex.setTextColor(TextFields.NAME,txtassestdescription))return false;
        if (!Regex.setTextColor(TextFields.NAME,txtassestname))return false;
        if (!Regex.setTextColor(TextFields.DOUBLE,txtassetvalue))return false;
        return true;
    }
}

