package com.sosha.ui;
import com.sosha.core.service.ProductService;
import com.sosha.core.domain.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class CatalogController {
  @Autowired private ProductService productService;
  @FXML private TextField searchField;
  @FXML private TableView<Product> table;
  @FXML public void initialize(){
    searchField.textProperty().addListener((obs,old,newV)->search(newV));
  }
  private void search(String q){
    String tenantId = "demo-tenant"; // in real code fetch from TenantContext
    List<Product> results = q.isBlank()? productService.list(tenantId) : productService.search(tenantId,q);
    table.setItems(FXCollections.observableArrayList(results));
  }
}
