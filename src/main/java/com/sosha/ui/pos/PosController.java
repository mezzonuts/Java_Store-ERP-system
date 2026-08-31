package com.sosha.ui.pos;
import com.sosha.core.domain.SaleItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.util.UUID;
public class PosController {
  @FXML private TextField barcodeField;
  @FXML private TableView<SaleItem> cartTable;
  @FXML private Label totalLabel, statusLabel;
  @FXML private Button payButton;
  @FXML private TextField cashField;
  private final CartViewModel vm = new CartViewModel();
  @FXML public void initialize(){
    cartTable.setItems(vm.getItems());
    vm.totalProperty().addListener((o,old,neo)-> totalLabel.setText("Rp "+neo.toString()));
    barcodeField.setOnAction(e-> onScan());
    payButton.setOnAction(e-> onPay());
  }
  private void onScan(){
    String barcode = barcodeField.getText();
    if(barcode.isBlank()) return;
    SaleItem item = new SaleItem();
    item.setId(UUID.randomUUID().toString());
    item.setProductId(barcode);
    item.setQty(BigDecimal.ONE);
    item.setUnitPrice(new BigDecimal("25000"));
    vm.addItem(item);
    barcodeField.clear();
  }
  private void onPay(){
    try{
      statusLabel.setText("Checkout OK - Rp "+vm.totalProperty().get());
      statusLabel.setStyle("-fx-text-fill:green;");
      vm.clear();
    }catch(Exception e){
      statusLabel.setText("Error: "+e.getMessage());
      statusLabel.setStyle("-fx-text-fill:red;");
    }
  }
  public void focusSearch(){ barcodeField.requestFocus(); }
}
