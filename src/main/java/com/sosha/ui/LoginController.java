package com.sosha.ui;
import com.sosha.core.security.AuthService;
import com.sosha.core.security.TenantContext;
import com.sosha.security.JwtService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginController {
    @Autowired private AuthService authService;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML public void onLogin() {
        try {
            String token = authService.login(usernameField.getText(), passwordField.getText());
            statusLabel.setText("Login OK");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            statusLabel.setText("Login Gagal: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
