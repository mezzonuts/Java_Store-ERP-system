package com.sosha.core.service;
import com.sosha.core.domain.StockLevel;
import com.sosha.core.repository.StockLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; import java.util.Optional;
@Service
public class InventoryService {
  @Autowired private StockLevelRepository stockRepo;
  @Transactional public void adjustStock(String productId, String warehouseId, BigDecimal qty, String reason){
    StockLevel sl = stockRepo.findByProductIdAndWarehouseId(productId, warehouseId).orElse(new StockLevel());
    if(sl.getId()==null) {
      sl.setId(java.util.UUID.randomUUID().toString());
      sl.setProductId(productId); sl.setWarehouseId(warehouseId); sl.setAvailableQty(BigDecimal.ZERO);
    }
    sl.setAvailableQty(sl.getAvailableQty().add(qty));
    stockRepo.save(sl);
  }
  @Transactional public StockLevel getStock(String productId, String warehouseId){
    return stockRepo.findByProductIdAndWarehouseId(productId, warehouseId).orElse(null);
  }
  @Transactional public boolean checkStock(String productId, String warehouseId, BigDecimal required, boolean allowNegative){
    StockLevel sl = getStock(productId, warehouseId);
    if(sl==null) return false;
    return sl.getAvailableQty().compareTo(required)>=0 || allowNegative;
  }
}
