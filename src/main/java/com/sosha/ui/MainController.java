package com.sosha.ui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class MainController {
  @Autowired private ApplicationContext ctx;
  @FXML private BorderPane root;
  @FXML private StackPane contentPane;
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
  @FXML public void navPos() { navigate("/fxml/pos.fxml"); }
  @FXML public void navCatalog() { navigate("/fxml/catalog.fxml"); }
  @FXML public void navInventory() { navigate("/fxml/inventory.fxml"); }
  @FXML public void navFinance() { navigate("/fxml/pos.fxml"); }
  private void navigate(String fxml){
    try{
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
      loader.setControllerFactory(ctx::getBean);
      Parent node = loader.load();
      contentPane.getChildren().setAll(node);
    }catch(Exception e){ e.printStackTrace(); }
  }
}
