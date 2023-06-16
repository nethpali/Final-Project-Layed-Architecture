package lk.ijse.Roosalu.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Roosalu.Util.Regex;
import lk.ijse.Roosalu.Util.TextFields;
import lk.ijse.Roosalu.bo.BoFactory;
import lk.ijse.Roosalu.bo.Custom.AgentBO;
import lk.ijse.Roosalu.bo.Custom.EmployeeBO;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.EmployeeDto;
import lk.ijse.Roosalu.dto.tm.EmployeeTM;
import lk.ijse.Roosalu.model.EmployeeModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeManageController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/roosalu";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public AnchorPane root3;


    @FXML
    private Button btnupdate;

    @FXML
    private Button btnsave;

    @FXML
    private Button btndelete;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSalary;

    @FXML
    private JFXTextField txtemployeeid;

    @FXML
    private JFXTextField txtemployeenic;

    @FXML
    private JFXTextField txtemployeeaddress;

    @FXML
    private JFXTextField txtemployeename;

    @FXML
    private JFXTextField txtemployeecontactNo;

    @FXML
    private JFXTextField txtemployeeslarayperhour;

    @FXML
    private TableView<EmployeeTM> tblemployee;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colemployeeId;

    @FXML
    private TableColumn<?, ?> colemployeenic;

    @FXML
    private TableColumn<?, ?> colemployeeaddress;

    @FXML
    private TableColumn<?, ?> colemployeename;

    @FXML
    private TableColumn<?, ?> colemployeecontactNo;

    @FXML
    private TableColumn<?, ?> colemplyeesalaryperHour;

    @FXML
    private ComboBox<?> txtUserId;

    private ObservableList<EmployeeTM> observableList;


  EmployeeBO employeeBO=(EmployeeBO) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.EMPLOYEE);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void getAll() throws ClassNotFoundException {
        tblemployee.getItems().clear();
        try {
            List<EmployeeDto> employeeList = employeeBO.getAllEmployee();

            for (EmployeeDto employee : employeeList) {
                tblemployee.getItems().add(new EmployeeTM(employee.getEmployee_id(),employee.getEmployee_nic(),employee.getEmployee_name(),employee.getEmployee_address(),employee.getEmployee_contact_no(),employee.getEmployee_salary_per_hour()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }
    private void setCellValueFactory() {
        colemployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colemployeenic.setCellValueFactory(new PropertyValueFactory<>("employee_nic"));
        colemployeename.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
        colemployeeaddress.setCellValueFactory(new PropertyValueFactory<>("employee_address"));
        colemployeecontactNo.setCellValueFactory(new PropertyValueFactory<>("employee_contact_no"));
        colemplyeesalaryperHour.setCellValueFactory(new PropertyValueFactory<>("employee_salary_per_hour"));
    }

    @FXML
    void btnCalculateOnAction(ActionEvent event) throws IOException, SQLException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/MarkAttendance.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnsaveEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"").show();
            return;
        }

        EmployeeDto employee = new EmployeeDto(txtemployeeid.getText(),txtemployeenic.getText(),txtemployeename.getText(),txtemployeeaddress.getText(),txtemployeecontactNo.getText(),Double.parseDouble(txtemployeeslarayperhour.getText()));
        if(employeeBO.addEmployee(employee)){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Added", ButtonType.OK).show();
        } else{
            new Alert(Alert.AlertType.WARNING,"Employee Not Saved", ButtonType.OK).show();
        }
        getAll();
        clearAll();

    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        EmployeeDto employee = new EmployeeDto(txtemployeeid.getText(),txtemployeenic.getText(),txtemployeename.getText(),txtemployeeaddress.getText(),txtemployeecontactNo.getText(),Double.parseDouble(txtemployeeslarayperhour.getText()));

        if (employeeBO.updateEmployee(new EmployeeDto(employee.getEmployee_id(), employee.getEmployee_nic(), employee.getEmployee_name(), employee.getEmployee_address(), employee.getEmployee_contact_no(), employee.getEmployee_salary_per_hour()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try agin", ButtonType.OK).show();
        }
        getAll();
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(employeeBO.deleteEmployee(txtemployeeid.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnEmployeeSearchOnAction(ActionEvent actionEvent) {
        String  id=txtSearch.getText();
        try{
            EmployeeDto employeeDto=employeeBO.searchEmployee(id);
            if(employeeDto !=null){
                fillData(employeeDto);
            }else{
                new Alert(Alert.AlertType.WARNING,"Employee Not Found").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        txtSearch.setText("");
    }
    public void fillData(EmployeeDto employeeDto){
        txtemployeeid.setText(employeeDto.getEmployee_id());
        txtemployeenic.setText(employeeDto.getEmployee_nic());
        txtemployeename.setText(employeeDto.getEmployee_name());
        txtemployeecontactNo.setText(employeeDto.getEmployee_contact_no());
        txtemployeeslarayperhour.setText(String.valueOf(employeeDto.getEmployee_salary_per_hour()));
        txtemployeeaddress.setText(employeeDto.getEmployee_address());
    }


    private void clearAll() {
        txtemployeeid.setText(null);
        txtemployeenic.setText(null);
        txtemployeename.setText(null);
        txtemployeeaddress.setText(null);
        txtemployeecontactNo.setText(null);
        txtemployeeslarayperhour.setText(null);
    }

    public void txtEmployeeIdOnAction(KeyEvent keyEvent) {
      Regex.setTextColor(TextFields.INVOICE,txtemployeeid);
    }

    public void txtEmployeeNic(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.LANKAN_ID,txtemployeenic);
    }

    public void txtEmployeeAddress(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtemployeeaddress);
    }

    public void txtEmployeeName(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtemployeename);
    }

    public void txtEmployeeContatctNo(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtemployeecontactNo);
    }

    public void txtEmployeeSalaryPerHour(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtemployeeslarayperhour);
    }
    public boolean isValidated(){
        if (!Regex.setTextColor(TextFields.INVOICE,txtemployeeid))return false;
        if (!Regex.setTextColor(TextFields.NAME,txtemployeename))return false;
        if (!Regex.setTextColor(TextFields.ADDRESS,txtemployeeaddress))return false;
        if (!Regex.setTextColor(TextFields.LANKAN_ID,txtemployeenic))return false;
        if (!Regex.setTextColor(TextFields.DOUBLE,txtemployeeslarayperhour))return false;
        if (!Regex.setTextColor(TextFields.PHONE,txtemployeecontactNo))return false;
        return true;
    }
}
