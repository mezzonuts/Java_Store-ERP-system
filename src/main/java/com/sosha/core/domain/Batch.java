package com.sosha.core.domain;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="batches")
public class Batch {
  @Id private String id;
  @Column(name="product_id", nullable=false) private String productId;
  @Column(name="batch_no", nullable=false) private String batchNo;
  @Column(name="expiry_date") private LocalDate expiryDate;
  @Column(name="qty", nullable=false) private int qty;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getProductId(){return productId;} public void setProductId(String v){productId=v;}
  public String getBatchNo(){return batchNo;} public void setBatchNo(String v){batchNo=v;}
  public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate v){expiryDate=v;}
  public int getQty(){return qty;} public void setQty(int v){qty=v;}
}
