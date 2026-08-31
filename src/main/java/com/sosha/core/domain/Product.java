package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="products", uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id","sku"}))
public class Product {
  @Id private String id;
  @Column(name="tenant_id", nullable=false) private String tenantId;
  @Column(nullable=false) private String sku;
  @Column private String barcode;
  @Column(nullable=false) private String name;
  @Column(name="uom_id") private String uomId;
  @Column(name="category_id") private String categoryId;
  @Column(name="is_serialized") private boolean serialized;
  @Column(name="is_batched") private boolean batched;
  @Column(name="base_price", nullable=false) private BigDecimal basePrice;
  @Column(name="attributes_json") private String attributesJson;
  @Column(name="is_published") private boolean published;
  @Column(name="sync_policy", nullable=false) private String syncPolicy="PUBLIC";
  @Column(name="deleted_at") private String deletedAt;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getSku(){return sku;} public void setSku(String v){sku=v;}
  public String getBarcode(){return barcode;} public void setBarcode(String v){barcode=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public BigDecimal getBasePrice(){return basePrice;} public void setBasePrice(BigDecimal v){basePrice=v;}
  public boolean isPublished(){return published;} public void setPublished(boolean v){published=v;}
  public String getSyncPolicy(){return syncPolicy;} public void setSyncPolicy(String v){syncPolicy=v;}
  public boolean isSerialized(){return serialized;} public void setSerialized(boolean v){serialized=v;}
  public boolean isBatched(){return batched;} public void setBatched(boolean v){batched=v;}
}
