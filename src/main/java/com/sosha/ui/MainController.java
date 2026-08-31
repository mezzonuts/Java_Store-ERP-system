package com.sosha.ui;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class MainController {
  @Autowired private ApplicationContext ctx;
  @FXML private BorderPane root;
  @FXML private CheckBox darkModeCheck;
  @FXML public void initialize(){
    darkModeCheck.setSelected(false);
    darkModeCheck.setOnAction(e-> toggleTheme());
  }
  private void toggleTheme(){
    String css = darkModeCheck.isSelected()? "/css/dark.css" : "/css/light.css";
    root.getScene().getStylesheets().clear();
    root.getScene().getStylesheets().add(getClass().getResource(css).toExternalForm());
  }
}
