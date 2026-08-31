package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="payments")
public class Payment {
  @Id private String id;
  @Column(name="sale_id", nullable=false) private String saleId;
  @Column(name="amount", nullable=false) private BigDecimal amount;
  @Column(nullable=false) private String method; // CASH,CARD,EWALLET
  @Column(name="created_at") private String createdAt;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getSaleId(){return saleId;} public void setSaleId(String v){saleId=v;}
  public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
  public String getMethod(){return method;} public void setMethod(String v){method=v;}
}
