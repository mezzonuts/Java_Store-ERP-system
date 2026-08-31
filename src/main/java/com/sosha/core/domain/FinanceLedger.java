package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="finance_ledger", indexes=@Index(columnList="tenant_id"))
public class FinanceLedger {
  @Id private String id;
  @Column(name="tenant_id", nullable=false) private String tenantId;
  @Column(nullable=false) private String type;
  @Column(nullable=false) private BigDecimal amount;
  @Column private String description;
  @Column(name="created_at") private String createdAt;
  @Column(name="user_id") private String userId;

  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getType(){return type;} public void setType(String v){type=v;}
  public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public String getCreatedAt(){return createdAt;} public void setCreatedAt(String v){createdAt=v;}
  public String getUserId(){return userId;} public void setUserId(String v){userId=v;}
}
