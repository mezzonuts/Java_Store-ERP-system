package com.sosha.ui;
import com.sosha.core.service.InventoryService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
@Component
public class InventoryController {
  @Autowired private InventoryService invService;
  @FXML private TextField productIdField, warehouseIdField, qtyField, reasonField;
  @FXML private Label statusLabel;
  @FXML public void onAdjust(){
    try{
      BigDecimal qty = new BigDecimal(qtyField.getText());
      invService.adjustStock(productIdField.getText(), warehouseIdField.getText(), qty, reasonField.getText());
      statusLabel.setText("Stock adjusted OK");
      statusLabel.setStyle("-fx-text-fill:green;");
    }catch(Exception e){
      statusLabel.setText("Error: "+e.getMessage());
      statusLabel.setStyle("-fx-text-fill:red;");
    }
  }
}
