package com.sosha.ui;
import com.sosha.core.security.AuthService;
import com.sosha.core.security.TenantContext;
import com.sosha.security.JwtService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
            String token = authService.login(usernameField.getText(), passwordField.getText());
            statusLabel.setText("Login OK");
            statusLabel.setStyle("-fx-text-fill: green;");
            javafx.application.Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                    loader.setControllerFactory(ctx::getBean);
                    Scene scene = new Scene(loader.load(), 1280, 720);
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Sosha POS v2.0 - Main");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            statusLabel.setText("Login Gagal: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
