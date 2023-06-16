package lk.ijse.Roosalu.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

    public class LoginFormController implements Initializable {

        public TextField txtUsername;
        public Button btnLoginBack;
        public PasswordField txtPasswordField;
        public ImageView imgShowPassword;
        public ImageView imgDontShowPassword;
        public TextField txtPasswordShowField;

        @FXML
        private Button btnLogin;
        @FXML


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            txtPasswordShowField.setVisible(false);
            imgDontShowPassword.setVisible(false);
        }

        @FXML
        void btnLoginOnAction(ActionEvent event) throws IOException {
            if (txtPasswordShowField.isVisible()){
                txtPasswordField.setText(txtPasswordShowField.getText());
            }
            if (txtUsername.getText().equalsIgnoreCase("Admin") & txtPasswordField.getText().equalsIgnoreCase("1234")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
                Scene scene = new Scene(anchorPane);
                DashBoardController controller = loader.getController();
               // controller.setTxtName(txtUsername);

                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Login Form");
                stage.centerOnScreen();

            } else {
                txtUsername.setStyle("-fx-background-color: red");
                txtPasswordField.setStyle("-fx-background-color: red");
            }

        }

        public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
            Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("welcome Page");
            stage.centerOnScreen();

        }

        public void imgDontShowPasswordOnAction(MouseEvent mouseEvent) {
            txtPasswordField.setText(txtPasswordShowField.getText());
            txtPasswordField.setVisible(true);
            imgShowPassword.setVisible(true);
            txtPasswordShowField.setVisible(false);
            imgDontShowPassword.setVisible(false);
        }

        public void imgShowPasswordOnAction(MouseEvent mouseEvent) {
            txtPasswordShowField.setText(txtPasswordField.getText());
            txtPasswordField.setVisible(false);
            imgShowPassword.setVisible(false);
            txtPasswordShowField.setVisible(true);
            imgDontShowPassword.setVisible(true);
        }
    }
