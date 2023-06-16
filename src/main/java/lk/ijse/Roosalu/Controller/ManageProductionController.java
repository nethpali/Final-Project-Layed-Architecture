package lk.ijse.Roosalu.Controller;

import com.jfoenix.controls.JFXTextField;
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
import lk.ijse.Roosalu.bo.Custom.ProductBO;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.Order;
import lk.ijse.Roosalu.dto.ProductDto;
import lk.ijse.Roosalu.dto.RawMaterialDto;
import lk.ijse.Roosalu.dto.tm.ProductionTM;
import lk.ijse.Roosalu.model.OrderModel;
import lk.ijse.Roosalu.model.RawMaterialModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageProductionController implements Initializable {

    public TableColumn <?,?> colOrderId;
    public ComboBox <String>cmbOrderId;
    public DatePicker dpDate;
    public Button btnPlaceOrder;
    public TableColumn colProdutUnitPrice;
    public JFXTextField txtUnitPrice;

    @FXML
    private DatePicker Dob;
    @FXML
    private JFXTextField txtRawMaterialId;
    public TableColumn <?,?> colProductionDate;
    public TableColumn <?,?> colProductionqty;
    public JFXTextField txtProductionDate;
    @FXML
    private TableColumn<?, ?> ColRawMateriallqty;

    @FXML
    private Label lblDate;

    @FXML
    private JFXTextField txtProductionId;

    @FXML
    private JFXTextField txtproductionQty;

    @FXML
    private TextField txtProductionSearch;

    @FXML
    private TableView<ProductionTM> tblProduction;

    @FXML
    private TableColumn<?, ?> colProductionId;

    @FXML
    private TableColumn<?, ?> colRawMaterialId;

    @FXML
    private TableColumn<?, ?> Colqty;

    @FXML
    private TableColumn<?, ?> colDate;

    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtAgentId;

    @FXML
    private ComboBox<String> cmbrawmaterialId;
    private ObservableList<ProductionTM> observableList;
    private String rawmaterialid;
    public static ArrayList<RawMaterialDto> rawMaterialArrayList= new ArrayList();
    private String OrderId;
    private ArrayList<Order> OrderArrayList;

    ProductBO productBO= (ProductBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.PRODUCT);
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        loadComboBox();


        //lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCellValueFactory() {
        colProductionId.setCellValueFactory(new PropertyValueFactory<>("production_id"));
        colProductionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProductionqty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProdutUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

    }
    private void getAll(){
       tblProduction.getItems().clear();
        try {
            ArrayList<ProductDto>productionList = productBO.getAllProduct();

            for (ProductDto production: productionList) {
               tblProduction.getItems().add(new ProductionTM(production.getProduction_id(),production.getDate(),production.getQuantity(),production.getUnit_price()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }

    @FXML
    void btnProductionSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()) {
            new Alert(Alert.AlertType.ERROR, "").show();
            return;
        }
        String date = String.valueOf(dpDate.getValue());
        ProductDto production = new ProductDto(txtProductionId.getText(), date, Integer.parseInt(txtproductionQty.getText()), Double.parseDouble(txtUnitPrice.getText()));
            if(productBO.addProduct(new ProductDto(production.getProduction_id(), production.getDate(), production.getQuantity(), production.getUnit_price()))){
                new Alert(Alert.AlertType.CONFIRMATION,"Production Added", ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Production Not Saved", ButtonType.OK).show();
            }
            getAll();
            clearAll();
        }

        @FXML
        void btnProductionUpdateOnAction (ActionEvent event) throws SQLException, ClassNotFoundException {
            if (!isValidated()) {
                new Alert(Alert.AlertType.ERROR, "").show();
                return;
            }
            String date = String.valueOf(dpDate.getValue());
            ProductDto production = new ProductDto(txtProductionId.getText(),date, Integer.parseInt(txtproductionQty.getText()), Double.parseDouble(txtUnitPrice.getText()));
            if(productBO.updateProduct(new ProductDto(production.getProduction_id(),production.getDate(),production.getQuantity(),production.getUnit_price()))) {
                new Alert(Alert.AlertType.CONFIRMATION, "product Updated", ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
            getAll();
            clearAll();
        }
    }

    @FXML
    void btnProductionDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(productBO.deleteProduct(txtProductionId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Product Deleted", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
    void btnManageSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String  id=txtProductionSearch.getText();
        try{
            ProductDto productDto=productBO.searchProduct(id);
            if(productDto !=null){
                fillData(productDto);
            }else{
                new Alert(Alert.AlertType.WARNING,"Product  Not Found").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        txtProductionSearch.setText("");
    }
    public void fillData(ProductDto productDto){
        txtProductionId.setText(productDto.getProduction_id());
        dpDate.setValue(LocalDate.parse(productDto.getDate()));
        txtproductionQty.setText(String.valueOf(productDto.getQuantity()));
        txtUnitPrice.setText(String.valueOf(productDto.getUnit_price()));
    }

    @FXML
    void cmbRawMaterialIdOnAction(ActionEvent event) throws SQLException {
        rawmaterialid= (String) cmbrawmaterialId.getValue();
        try {
            RawMaterialDto rawMaterial= RawMaterialModel.search(rawmaterialid);
            txtRawMaterialId.setText(rawmaterialid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void cmbOrderIdOnAction(ActionEvent actionEvent) {
        OrderId= (String) cmbOrderId.getValue();
        try {
            Order order= OrderModel.search(OrderId);
            cmbOrderId.setValue(OrderId);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void loadComboBox(){
    }
    private void clearAll() {
        txtProductionId.setText(null);
        dpDate.setValue(null);
        txtproductionQty.setText(null);
        txtUnitPrice.setText(null);

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void txtProductUnitPrice(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtUnitPrice);
    }

    public void txtProductionId(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtProductionId);
    }

    public void txtproductionqty(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtproductionQty);
    }
    public boolean isValidated(){
        if (!Regex.setTextColor(TextFields.INVOICE,txtProductionId))return false;
        if (!Regex.setTextColor(TextFields.DOUBLE,txtUnitPrice))return false;
        if (!Regex.setTextColor(TextFields.INTEGER,txtproductionQty))return false;
        return true;
    }
}
