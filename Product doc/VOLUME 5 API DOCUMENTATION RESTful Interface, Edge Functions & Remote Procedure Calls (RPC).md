# SOSHA POS & INVENTORY MANAGEMENT SYSTEM
## VOLUME 5: API DOCUMENTATION - v2.0 Local API + Python Sidecar + Store Cloud

---

## 1. API Architecture v2 (Hybrid Local + Cloud)

Sosha v2 tidak lagi pakai PostgREST/Supabase. API terbagi 3:

1.  **Local Embedded API (Java Spring Boot):** Jalan di dalam Desktop (`http://localhost:8080/api/v1/*`), diakses JavaFX via Spring Beans (in-process) atau REST lokal. Untuk semua operasi offline.
2.  **Python Sidecar API (FastAPI):** `http://localhost:8001/api/v1/*` - AI forecast, anomaly, RAG lokal.
3.  **Store Cloud API (Spring Boot Docker):** `https://store.sosha.com/api/v1/*` - hanya untuk data PUBLIC (katalog publish, stok publish, order online).

> **Keputusan:** Direct in-process call untuk 80% (tanpa HTTP overhead), RETROFIT HTTP hanya untuk Python sidecar & Store Cloud.

---

## 2. Authentication

### 2.1 Lokal (Desktop)
*   Login offline: `BCrypt` verify vs `users.password_hash` lokal
*   JWT lokal (HS256, secret di OS keychain, expiry 8 jam)
*   Header: `Authorization: Bearer <local-jwt>` dengan claims `userId, tenantId, branchId, role`

### 2.2 Store Cloud
*   API Key per tenant: `X-Tenant-Api-Key: sosha_pk_xxx` + HMAC
*   Rate limit 1000 req/min per tenant

---

## 3. Global Standards

| Verb | Use |
| :--- | :--- |
| GET | Query lokal <50ms |
| POST | Create (idempotencyKey required) |
| PATCH | Update partial |
| DELETE | Soft delete (`deleted_at`) |

**Error Standard**
```json
{"code":"409","message":"INSUFFICIENT_STOCK","details":{"sku":"ABC","available":2},"idempotencyKey":"uuid"}
```

---

## 4. Core Local API (Embedded)

### 4.1 Products `/api/v1/products`
| Method | Path | Desc |
| :--- | :--- | :--- |
| GET | `/products?search=kopi&limit=50` | FTS search (dialect aware) |
| GET | `/products/{id}` | Detail |
| POST | `/products` | Create (sync_policy=PUBLIC/PRIVATE) |
| PATCH | `/products/{id}` | Update, if `is_published` -> enqueue outbox |
| GET | `/products/low-stock` | View v_low_stock |

**Contoh Create**
```json
POST /api/v1/products
{"sku":"ABC-123","name":"Kopi Arabika","basePrice":25000,"is_published":true,"sync_policy":"PUBLIC"}
-> 201 {id, sku} + outbox enqueued
```

### 4.2 POS `/api/v1/sales`
```json
POST /api/v1/sales/checkout
{
  "idempotencyKey":"uuid","branchId":"uuid","items":[{"productId":"uuid","qty":2}],"payments":[{"method":"CASH","amount":50000}]
}
-> 201 {saleId, total, change} // ACID lokal, SELECT FOR UPDATE / BEGIN IMMEDIATE
```

### 4.3 Inventory RPC style (Local Service)
```json
POST /api/v1/inventory/move
{"productId":"uuid","fromWarehouse":"uuid","toWarehouse":"uuid","qty":50}
```
Atomic @Transactional, ledger + outbox if PUBLIC.

---

## 5. Python Sidecar API (FastAPI :8001)

| Endpoint | Desc | Input | Output |
| :--- | :--- | :--- | :--- |
| `POST /api/v1/forecast` | Prediksi 30 hari | `{productId, history:[...]}` | `{forecast: [...], recommendedPO: 120}` |
| `POST /api/v1/anomaly/check` | Cek anomali transaksi | `{sale:{total, items}}` | `{anomaly:false, reason:null}` |
| `POST /api/v1/rag/query` | Tanya data natural | `{query:"stok menipis?"}` | `{sql:"SELECT...", answer:"..."}` |
| `GET /health` | Watchdog | - | `{status:"ok", modelLoaded:true}` |

Java memanggil via:
```java
retrofit.create(PythonClient.class).forecast(req).execute();
```
Timeout 2s, fallback skip AI jika sidecar down.

---

## 6. Store Cloud API (Public Only)

| Method | Endpoint | Desc |
| :--- | :--- | :--- |
| `GET` | `/store/api/v1/catalog?tenant=xxx` | List produk `is_published=true` |
| `POST` | `/store/api/v1/catalog/publish` | Push dari desktop outbox (idempotencyKey) |
| `POST` | `/store/api/v1/stock/publish` | Update stok publish |
| `GET` | `/store/api/v1/orders?since=ts` | Pull order online ke desktop |
| `POST` | `/store/api/v1/orders/{id}/status` | Update status dari desktop |

**Outbox Push Flow**
```
Desktop Quartz (every 5s if online) -> POST /stock/publish {idempotencyKey, sku, qty}
Cloud validates apiKey + idempotency -> UPSERT PG cloud -> 200
Desktop marks synced
```

---

## 7. Dialect & Pagination

*   Semua list: `?page=0&size=50` default 50, max 200
*   Search abstrak: `ProductRepository.search(String q)` -> SQLite `MATCH` vs PG `@@`
*   Hibernate handle via `@Query(nativeQuery=true)` dengan profile

---

## 8. Error Handling

| Code | Meaning |
| :--- | :--- |
| 400 | Validasi (CHECK constraint) |
| 401 | JWT lokal invalid |
| 403 | RBAC (cashier coba akses finance PRIVATE) |
| 409 | Stok habis / duplicate sku / idempotency replay (return previous success) |
| 503 | Sidecar unavailable (fallback) |

---

## 9. Checklist

- [ ] OpenAPI spec untuk Store Cloud (`/v3/api-docs`)
- [ ] Idempotency table lokal
- [ ] Retrofit client dengan exponential backoff untuk Store Cloud
- [ ] Python sidecar OpenAPI di `:8001/docs`

## 10. Best Practices

1.  Jangan panggil HTTP untuk operasi lokal (pakai Bean).
2.  Batch outbox 50 rows, Gzip
3.  Selalu kirim `idempotencyKey` untuk POST

**End of Volume 5 v2.0**
