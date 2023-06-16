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
import lk.ijse.Roosalu.Entity.Agent;
import lk.ijse.Roosalu.Util.Regex;
import lk.ijse.Roosalu.Util.TextFields;
import lk.ijse.Roosalu.bo.BoFactory;
import lk.ijse.Roosalu.bo.Custom.AgentBO;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.UserDto;
import lk.ijse.Roosalu.dto.tm.AgentTM;
import lombok.SneakyThrows;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.util.*;


public class AgentManageController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/roosalu";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public TextField txtSearch;
    public ComboBox cmbUserId;

    @FXML
    private JFXTextField txtUserId;
    @FXML
    private Button btnsaveAgent;

    @FXML
    private Button btnUpdateAgent;

    @FXML
    private Button btnAgentDelete;


    @FXML
    private JFXTextField txtAgentId;

    @FXML
    private JFXTextField txtAgentCompany;

    @FXML
    private JFXTextField txtAgentName;

    @FXML
    private JFXTextField txtAgentContactNo;

    @FXML
    private JFXTextField txtAgentEmail;

    @FXML
    private TableView<AgentTM> tblAgent;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colAgentId;

    @FXML
    private TableColumn<?, ?> colAgentCompany;

    @FXML
    private TableColumn<?, ?> colAgentName;

    @FXML
    private TableColumn<?, ?> colAgentContactNo;
    @FXML
    private TableColumn<?, ?> colAgentEmail;

    private ObservableList<AgentTM> observableList;
    String userid;
    public static ArrayList<UserDto> userArrayList= new ArrayList();

    AgentBO agentBO=(AgentBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.AGENT);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        loadComboBox();
    }
    private void getAll() throws ClassNotFoundException {
        tblAgent.getItems().clear();
        try {
            ArrayList<AgentDto> agentslist = agentBO.getAllAgents();

            for (AgentDto agent : agentslist) {
                tblAgent.getItems().add(new AgentTM(agent.getId(),agent.getEmail(),agent.getName(),agent.getContact_no(),agent.getCompany()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }

    }
    private void setCellValueFactory() {
        colAgentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAgentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAgentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAgentContactNo.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colAgentCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
    }

    @FXML
    void btnAddAgentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"").show();
            return;
        }
        AgentDto agent = new AgentDto(txtAgentId.getText(),txtAgentEmail.getText(),txtAgentName.getText(),txtAgentContactNo.getText(),txtAgentCompany.getText());

        if(agentBO.addAgents(agent)){
            new Alert(Alert.AlertType.CONFIRMATION,"Agent Added", ButtonType.OK).show();
        } else{
            new Alert(Alert.AlertType.WARNING,"Agent Not Saved", ButtonType.OK).show();
        }

        getAll();
        clearAll();

    }

    @FXML
    void btnUpdateAgentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        AgentDto agent =new AgentDto(txtAgentId.getText(),txtAgentEmail.getText(),txtAgentName.getText(),txtAgentContactNo.getText(),txtAgentCompany.getText());
        if(agentBO.updateAgents(new AgentDto(agent.getId(),agent.getEmail(),agent.getName(),agent.getContact_no(),agent.getCompany()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Agent Updated", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
    void btnDeleteAgentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(agentBO.deleteAgents(txtAgentId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Agent Deleted", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void txtAgentSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String  id=txtSearch.getText();
        try{
            AgentDto agentDto=agentBO.searchAgent(id);
            if(agentDto !=null){
                fillData(agentDto);
            }else{
                new Alert(Alert.AlertType.WARNING,"Agent Not Found").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
       txtSearch.setText("");
    }

    public void fillData(AgentDto agentDto){
        txtAgentId.setText(agentDto.getId());
        txtAgentEmail.setText(agentDto.getEmail());
        txtAgentName.setText(agentDto.getName());
        txtAgentContactNo.setText(agentDto.getContact_no());
        txtAgentCompany.setText(agentDto.getCompany());

        System.out.println(agentDto.getContact_no());
    }

    private void clearAll() {
        txtAgentId.setText(null);
        txtAgentEmail.setText(null);
        txtAgentName.setText(null);
        txtAgentContactNo.setText(null);
        txtAgentCompany.setText(null);
    }

   public void loadComboBox() throws SQLException, ClassNotFoundException {
    }
    public void cmbUserIdOnAction(ActionEvent actionEvent) {
    }

    public void rowOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void txtAgentId(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtAgentId);
    }

    public void txtAgentCompany(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtAgentCompany);
    }

    public void txtAgentName(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtAgentName);
    }

    public void txtAgentContactNo(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtAgentContactNo);
    }

    public void txtAgentEmail(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtAgentEmail);
    }
    public boolean isValidated(){
        if (!Regex.setTextColor(TextFields.INVOICE,txtAgentId))return false;
        if (!Regex.setTextColor(TextFields.NAME,txtAgentCompany))return false;
        if (!Regex.setTextColor(TextFields.NAME,txtAgentName))return false;
        //if (!Regex.setTextColor(TextFields.PHONE,txtAgentContactNo))return false;
        if (!Regex.setTextColor(TextFields.EMAIL,txtAgentEmail))return false;
        return true;
    }
}
