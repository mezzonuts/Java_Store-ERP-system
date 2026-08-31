package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="sale_items")
public class SaleItem {
  @Id private String id;
  @Column(name="sale_id", nullable=false) private String saleId;
  @Column(name="product_id", nullable=false) private String productId;
  @Column(nullable=false) private BigDecimal qty;
  @Column(name="unit_price", nullable=false) private BigDecimal unitPrice;
  @Column(name="discount", precision=15, scale=2) private BigDecimal discount = BigDecimal.ZERO;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getSaleId(){return saleId;} public void setSaleId(String v){saleId=v;}
  public String getProductId(){return productId;} public void setProductId(String v){productId=v;}
  public BigDecimal getQty(){return qty;} public void setQty(BigDecimal v){qty=v;}
  public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal v){unitPrice=v;}
  public BigDecimal getDiscount(){return discount;} public void setDiscount(BigDecimal v){discount=v;}
}
