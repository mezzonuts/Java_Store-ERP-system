package com.sosha.core.service;
import com.sosha.core.domain.Product;
import com.sosha.core.repository.ProductRepository;
import com.sosha.core.sync.OutboxEnqueuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List; import java.util.UUID;
@Service
public class ProductService {
  @Autowired private ProductRepository repo;
  @Autowired private OutboxEnqueuer outbox;
  @Transactional public Product create(Product p){
    if(p.getId()==null) p.setId(UUID.randomUUID().toString());
    Product saved=repo.save(p);
    if(saved.isPublished() && "PUBLIC".equals(saved.getSyncPolicy())) outbox.enqueue("products", saved.getId(), "{\"sku\":\""+saved.getSku()+"\"}");
    return saved;
  }
  public List<Product> search(String tenantId, String q){ return repo.search(tenantId, q); }
  public List<Product> list(String tenantId){ return repo.findByTenantIdAndDeletedAtIsNull(tenantId); }
}
