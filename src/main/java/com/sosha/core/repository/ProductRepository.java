package com.sosha.core.repository;
import com.sosha.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product,String> {
  List<Product> findByTenantIdAndDeletedAtIsNull(String tenantId);
  @Query(value="SELECT * FROM products WHERE tenant_id=:tenantId AND deleted_at IS NULL AND (name LIKE %:q% OR sku LIKE %:q% OR barcode=:q)", nativeQuery=true)
  List<Product> search(@Param("tenantId") String tenantId, @Param("q") String q);
}
