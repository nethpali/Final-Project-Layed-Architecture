
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
import lk.ijse.Roosalu.bo.Custom.AgentBO;
import lk.ijse.Roosalu.bo.Custom.UserBo;
import lk.ijse.Roosalu.db.DBConnection;
import lk.ijse.Roosalu.dto.AgentDto;
import lk.ijse.Roosalu.dto.UserDto;
import lk.ijse.Roosalu.dto.tm.UserTM;
import lk.ijse.Roosalu.model.UserModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserManageController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/roosalu";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public TextField txtSearch;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserNewPassword;

    @FXML
    private JFXTextField txtReEnterPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtUserEmail;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colNewPassword;

    @FXML
    private TableColumn<?, ?> colReEnterPassword;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserEmail;
    private ObservableList<UserTM> observableList;
    private boolean isMatchId=false;

    UserBo userBo=(UserBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.USER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        clearAll();
    }

    private void getAll() throws ClassNotFoundException, SQLException {
        tblUser.getItems().clear();
        try {
            ArrayList<UserDto> userlist = userBo.getAllUser();

            for (UserDto user : userlist) {
                tblUser.getItems().add(new UserTM(user.getUser_id(), user.getPassword(), user.getRe_enter_password(), user.getUser_name(), user.getUser_email()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }
    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colNewPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colReEnterPassword.setCellValueFactory(new PropertyValueFactory<>("re_enter_password"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
    }

    @FXML
    void btnUserAddOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"").show();
            return;
        }
        UserDto user = new UserDto(txtUserId.getText(),txtUserNewPassword.getText(),txtReEnterPassword.getText(),txtUserName.getText(),txtUserEmail.getText());
        if(userBo.addUser(new UserDto(user.getUser_id(), user.getPassword(), user.getRe_enter_password(),user.getUser_name(),user.getUser_email()))){
            new Alert(Alert.AlertType.CONFIRMATION,"User Added", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"User Not Saved", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
    void btnUserUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        UserDto user = new UserDto(txtUserId.getText(),txtUserNewPassword.getText(),txtReEnterPassword.getText(),txtUserName.getText(),txtUserEmail.getText());

        if(userBo.updateUser(new UserDto(user.getUser_id(), user.getPassword(), user.getRe_enter_password(), user.getUser_name(), user.getUser_email()))){
            new Alert(Alert.AlertType.CONFIRMATION,"User Updated", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
    void btnUserDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(userBo.deleteUser(txtUserId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"User Deleted", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,"User again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String  id=txtSearch.getText();
        try{
           UserDto userDto=userBo.searchUser(id);
            if(userDto !=null){
                fillData(userDto);
            }else{
                new Alert(Alert.AlertType.WARNING,"User Not Found").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        txtSearch.setText("");
    }
    public void fillData(UserDto userDto){
        txtUserId.setText(userDto.getUser_id());
        txtUserNewPassword.setText(userDto.getPassword());
        txtReEnterPassword.setText(userDto.getRe_enter_password());
        txtUserName.setText(userDto.getUser_name());
        txtUserEmail.setText(userDto.getUser_email());
    }


    private void clearAll() {
        txtUserId.setText(null);
        txtUserNewPassword.setText(null);
        txtReEnterPassword.setText(null);
        txtUserEmail.setText(null);
        txtUserName.setText(null);
    }

    public void txtUserId(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtUserId);
    }

    public void txtNewPassword(KeyEvent keyEvent) {
       // Regex.setTextColor(TextFields.INVOICE,txt);
    }

    public void txtReEnterPassword(KeyEvent keyEvent) {
    }

    public void txtUserName(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtUserName);
    }

    public void txtUserEmail(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtUserEmail);
    }
    public boolean isValidated(){
        if (!Regex.setTextColor(TextFields.INVOICE,txtUserId))return false;
        if (!Regex.setTextColor(TextFields.NAME,txtUserName))return false;
        if (!Regex.setTextColor(TextFields.EMAIL,txtUserEmail))return false;
        return true;
    }
}
