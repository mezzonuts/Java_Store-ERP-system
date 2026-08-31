package com.sosha.ui;
import com.sosha.core.security.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class LoginController {
    @Autowired private AuthService authService;
    @Autowired private ApplicationContext ctx;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML public void onLogin() {
        try {
            authService.login(usernameField.getText(), passwordField.getText());
            statusLabel.setText("Login OK - loading...");
            statusLabel.setStyle("-fx-text-fill: green;");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            loader.setControllerFactory(ctx::getBean);
            Scene scene = new Scene(loader.load(), 1200, 700);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Sosha POS v2.0");
        } catch (Exception fe) {
            statusLabel.setText("Login Gagal: " + fe.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
            fe.printStackTrace();
        }
    }
}
