package com.sosha.core.service;
import com.sosha.core.domain.*;
import com.sosha.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
@Service
public class SaleService {
  @Autowired private ProductRepository productRepo;
  @Autowired private StockLevelRepository stockRepo;
  @Autowired private InventoryService invService;
  public BigDecimal calculateTotal(List<SaleItem> items){
    BigDecimal total = BigDecimal.ZERO;
    for(SaleItem i: items){
      BigDecimal subtotal = i.getUnitPrice().multiply(i.getQty());
      if(i.getDiscount()!=null) subtotal = subtotal.subtract(i.getDiscount());
      total = total.add(subtotal);
    }
    return total;
  }
  @Transactional public Sale checkout(String tenantId, String branchId, String idempotencyKey, List<SaleItem> items){
    BigDecimal total = calculateTotal(items);
    Sale sale = new Sale();
    sale.setId(UUID.randomUUID().toString());
    sale.setTenantId(tenantId); sale.setBranchId(branchId);
    sale.setIdempotencyKey(idempotencyKey);
    sale.setTotalAmount(total);
    sale.setSyncPolicy("PRIVATE");
    // stock deduction (ACID)
    for(SaleItem item: items){
      invService.adjustStock(item.getProductId(), branchId, item.getQty().negate(), "SALE");
    }
    return sale;
  }
}
