package com.sosha.ui.pos;
import com.sosha.core.domain.SaleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
public class CartViewModel {
  private final ObservableList<SaleItem> items = FXCollections.observableArrayList();
  private final ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>(BigDecimal.ZERO);
  public ObservableList<SaleItem> getItems(){return items;}
  public ObjectProperty<BigDecimal> totalProperty(){return total;}
  public void addItem(SaleItem item){
    items.add(item);
    recalc();
  }
  public void removeItem(SaleItem item){
    items.remove(item);
    recalc();
  }
  public void clear(){ items.clear(); recalc();}
  private void recalc(){
    BigDecimal t = BigDecimal.ZERO;
    for(SaleItem i: items) t = t.add(i.getUnitPrice().multiply(i.getQty()));
    total.set(t);
  }
}
