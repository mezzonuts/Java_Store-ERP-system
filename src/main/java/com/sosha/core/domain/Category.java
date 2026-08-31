package com.sosha.core.domain;
import jakarta.persistence.*;
@Entity @Table(name="categories")
public class Category {
  @Id private String id;
  @Column(name="tenant_id", nullable=false) private String tenantId;
  @Column(nullable=false) private String name;
  @Column private String parentId;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public String getParentId(){return parentId;} public void setParentId(String v){parentId=v;}
}
