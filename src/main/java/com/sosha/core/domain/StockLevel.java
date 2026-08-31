package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="stock_levels", indexes={@Index(columnList="product_id,warehouse_id")})
public class StockLevel {
  @Id private String id;
  @Column(name="product_id", nullable=false) private String productId;
  @Column(name="warehouse_id", nullable=false) private String warehouseId;
  @Column(name="available_qty", nullable=false) private BigDecimal availableQty = BigDecimal.ZERO;
  @Column(name="reserved_qty", nullable=false) private BigDecimal reservedQty = BigDecimal.ZERO;
  @Column(name="min_stock_level") private BigDecimal minStockLevel;
  @Version private Long version;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getProductId(){return productId;} public void setProductId(String v){productId=v;}
  public String getWarehouseId(){return warehouseId;} public void setWarehouseId(String v){warehouseId=v;}
  public BigDecimal getAvailableQty(){return availableQty;} public void setAvailableQty(BigDecimal v){availableQty=v;}
  public BigDecimal getReservedQty(){return reservedQty;} public void setReservedQty(BigDecimal v){reservedQty=v;}
  public BigDecimal getMinStockLevel(){return minStockLevel;} public void setMinStockLevel(BigDecimal v){minStockLevel=v;}
}
