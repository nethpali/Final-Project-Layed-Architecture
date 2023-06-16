package lk.ijse.Roosalu.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Roosalu.Util.Service;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.*;
import lk.ijse.Roosalu.dto.tm.OrderTM;
import lk.ijse.Roosalu.model.AgentModel;
import lk.ijse.Roosalu.model.OrderModel;
import lk.ijse.Roosalu.model.PlaceOrderModal;
import lk.ijse.Roosalu.model.ProductionModel;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ManageOrderController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/roosalu";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public Label txtOrder1Id;
    public TextField txtSearchOrder;
    public AnchorPane root2;
    public TableColumn colDate;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderDelete;
    public TableColumn colProductId;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colqty;
    public JFXTextField txtUnitPrice;
    public ComboBox cmbProductId;
    public JFXTextField txtqty;
    public JFXTextField txtqtyonHand;
    public TextField txtqty1;
    public TextField txtBillOrder;
    public Label lblNetTotal;
    public Label lblOrderId;

    //public DatePicker dpOrderDeadLine;
    //public Label lblOrderId;
    @FXML
    private Label txtDate;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private ComboBox<String> cmbAgentId;

    @FXML
    private JFXTextField txtAgentName;

    @FXML
    private JFXTextField txtAgentCompany;

    @FXML
    private JFXTextField txtOrderDescription;

    @FXML
    private DatePicker dpOrderDeadLine;

    @FXML
    private JFXTextField txtAgentId;

    @FXML
    private TableView<OrderTM> tblOrder;

    @FXML
    private TableColumn<?, ?> colAgentId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDeadline;


    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();
    private String agentId;
    public static ArrayList<AgentDto> agentView= new ArrayList();
    private AnchorPane ControllArea;
    public static ArrayList<OrderDto> OrderView= new ArrayList();
    ArrayList<OrderDto> orderView=new ArrayList<>();
    private ObservableList<Object> observableList;
    private String productId="";
    private ArrayList<ProductDto> productView=new ArrayList<>();
    private JFXTextField txtProductionId;
    public ProductDto production=new ProductDto();

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNetTotal.setText("0");
        loadAgentIdComboBox();
        loadProductIdComboBox();
        txtOrderDate.setText(String.valueOf(LocalDate.now()));
        generateNewOrderId();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAgentId.setCellValueFactory(new PropertyValueFactory<>("agent_id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        //load();
    }

    public void generateNewOrderId() throws SQLException {
        try{
            String nextOrderId= OrderModel.generateNextOrderId();
            lblOrderId.setText(nextOrderId);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    private void clearAll() {
        txtOrderId.setText(null);
        txtOrderDate.setText(null);
        txtAgentId.setText(null);
        txtqty1.setText(null);
        removeAllRows();

    }
    public void removeAllRows(){
        for ( int i = 0; i<tblOrder.getItems().size(); i++) {
            tblOrder.getItems().clear();
        }
    }

    public void loadAgentIdComboBox() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<String> agentViewOb = FXCollections.observableArrayList();
            agentView= AgentModel.View();
            System.out.println(agentView);
            for (AgentDto agent:agentView) {
                agentViewOb.add(agent.getId());
            }
            cmbAgentId.setItems(agentViewOb);
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        //load();
    }

    @FXML
    void cmbAgentIdOnAction(ActionEvent event) {
        agentId= (String) cmbAgentId.getValue();
        try {
            AgentDto agent=AgentModel.search(agentId);
            txtAgentName.setText(agent.getName());
            txtAgentCompany.setText(agent.getCompany());
            txtAgentId.setText(agentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        txtOrderDate.setText(Service.setDate());
        txtAgentId.setText(agentId);
        URL resource = getClass().getResource("/view/AgentManage.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        root2.getChildren().clear();
        root2.getChildren().add(load);

        //load();
    }



//    public void load(){
//        try {
//            ObservableList<Object> OrderViewOb = FXCollections.observableArrayList();
//            orderView= OrderModel.View();
//            System.out.println(orderView);
//            for (Order order:orderView) {
//                OrderViewOb.add(order);
//            }
//            tblOrder.setItems(OrderViewOb);
//        } catch (SQLException | ClassNotFoundException e ) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    void btnOrderSearchOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orders WHERE Order_Id= ? ");

            pstm.setString(1,txtSearchOrder.getText());

            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                txtOrderId.setText(resultSet.getString(1));
                txtOrderDate.setText(resultSet.getString(2));
                txtAgentId.setText(resultSet.getString(3));
                txtProductionId.setText(resultSet.getString(4));
                txtUnitPrice.setText(resultSet.getString(5));
                txtqty.setText(resultSet.getString(6));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void LoadOnAction(ActionEvent actionEvent) {
        //load();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String OrderID = txtOrderDelete.getText();

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM orders WHERE Order_Id = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, OrderID);
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //load();
        txtOrderId.setText(null);
    }
    public void loadProductIdComboBox() {
        try {
            ObservableList<String> ProductViewOb = FXCollections.observableArrayList();
            productView= ProductionModel.View();
            System.out.println(productView);
            for (ProductDto production:productView) {
                ProductViewOb.add(production.getProduction_id());
            }
            cmbProductId.setItems(ProductViewOb);
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        //load();
    }

    public void cmbloadProductIdOnAction(ActionEvent actionEvent) {
        productId= cmbProductId.getValue().toString();
        try {
            production= ProductionModel.search(productId);
                txtUnitPrice.setText(String.valueOf(production.getUnit_price()));
                txtqtyonHand.setText(String.valueOf(production.getQuantity()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnAddTocartOnAction(ActionEvent actionEvent) {
        if (txtOrderId.getText()==null){
            new Alert(Alert.AlertType.CONFIRMATION,"Order ID Is Missing!!").show();
        }else {

            String agentId = cmbAgentId.getValue();
            String orderId = txtOrderId.getText();
            String productId = (String) cmbProductId.getValue();
            String date = txtOrderDate.getText();
            int qnty = Integer.parseInt(txtqty1.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            double total = unitPrice * qnty;
            if (Integer.parseInt(txtqtyonHand.getText())<qnty){
                new Alert(Alert.AlertType.CONFIRMATION,"Quantity is too much!!").show();
                txtqty1.setText(null);
            }else {
                OrderTM orderTM = new OrderTM(orderId, date, agentId, productId, unitPrice, qnty, total);
                obList.add(orderTM);
                tblOrder.setItems(obList);
                txtqty1.setText("");
                txtOrderId.setText(null);
                txtqtyonHand.setText(String.valueOf(Integer.parseInt(txtqtyonHand.getText())-qnty));
                double netTotal=Double.parseDouble(lblNetTotal.getText());
                lblNetTotal.setText(String.valueOf(orderTM.getTotal()+netTotal));

            }
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                OrderTM orderTM = obList.get(i);
                OrderDto order = new OrderDto(orderTM.getOrder_id(),
                        orderTM.getDate(),
                        orderTM.getAgent_id(),
                        orderTM.getProduct_id(),
                        orderTM.getUnit_price(),
                        orderTM.getQuantity(),
                        orderTM.getTotal()
                );
                int qtyProduction = ProductionModel.search(orderTM.getProduct_id()).getQuantity();
                ProductDto production = new ProductDto(orderTM.getProduct_id(),
                        orderTM.getDate(),
                        ProductionModel.search(orderTM.getProduct_id()).getQuantity() - orderTM.getQuantity(),
                        orderTM.getUnit_price()
                );
                System.out.println(production.getQuantity());
                boolean isPlaced = false;
                isPlaced = PlaceOrderModal.placeOrder(order, production);
                if (isPlaced) {
                    //System.out.println("Number 1 : "+isPlaced);
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
                }
            }
        }catch (SQLException e){

        }

        clearAll();

    }

    public void btnCreateBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {

        JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/Report/PlaceOrder.jrxml");
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasPrint,false);
    }

    String NetTotalCalculate(String id) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            double tot=0;

            String sql = "SELECT SUM(Total) FROM orders WHERE Order_Id=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);

            ResultSet affectedRows = pstm.executeQuery();
            System.out.println(affectedRows);
            while (affectedRows.next()){
                double c = affectedRows.getInt(1);
                tot=tot+c;
            }
            return String.valueOf(tot);
        }
    }
}

