package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="stock_ledger", indexes={@Index(columnList="product_id,warehouse_id")})
public class StockLedger {
  @Id private String id;
  @Column(name="product_id", nullable=false) private String productId;
  @Column(name="warehouse_id", nullable=false) private String warehouseId;
  @Column(name="change_qty", nullable=false) private BigDecimal changeQty;
  @Column(name="reference_type", nullable=false) private String referenceType; // SALE,PURCHASE,ADJUSTMENT,TRANSFER
  @Column(name="reference_id") private String referenceId;
  @Column(name="created_at", nullable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP") private String createdAt;
  @Column(name="user_id") private String userId;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getProductId(){return productId;} public void setProductId(String v){productId=v;}
  public String getWarehouseId(){return warehouseId;} public void setWarehouseId(String v){warehouseId=v;}
  public BigDecimal getChangeQty(){return changeQty;} public void setChangeQty(BigDecimal v){changeQty=v;}
  public String getReferenceType(){return referenceType;} public void setReferenceType(String v){referenceType=v;}
  public String getReferenceId(){return referenceId;} public void setReferenceId(String v){referenceId=v;}
}
