# SOSHA POS & INVENTORY MANAGEMENT SYSTEM
## VOLUME 9: TESTING - v2.0 Java+Python Offline Quality Assurance

---

## 1. Introduction
Testing v2 beralih dari Supabase-centric ke **Desktop-Native Assurance**. Bug di checkout offline = toko tidak bisa jualan tanpa workaround cloud. Strategi **Shift-Left** dengan matrix **Dual-DB (SQLite + PostgreSQL)** + **Privacy Audit**.

---

## 2. Testing Pyramid (Desktop)

1.  **Unit (60%):** JUnit5 + Mockito untuk `PricingService`, `InventoryService`, `OutboxEnqueuer`; `pytest` untuk Python `forecast`, `anomaly`.
2.  **Integration (25%):** Spring `@DataJpaTest` dengan Testcontainers (PG) & SQLite in-memory; TestFX untuk JavaFX.
3.  **E2E (10%):** TestFX headless POS flow + Playwright untuk Store Cloud web.
4.  **Specialized (5%):** JMH microbenchmark, k6 untuk Store Cloud, SQLCipher audit.

---

## 3. Unit Testing

### 3.1 Java (JUnit5 + Mockito + AssertJ)
```java
@ExtendWith(MockitoExtension.class)
class PricingServiceTest {
  @Test void tieredDiscount() {
    BigDecimal total = pricing.apply(new Cart(List.of(item(100,2))), Tier.GOLD);
    assertThat(total).isEqualByComparingTo("180.00");
  }
  @Test void insufficientStockThrows() {
    assertThrows(InsufficientStockException.class, ()-> inventory.checkout(cmdWithQty999()));
  }
}
```

### 3.2 DB Abstraction Test (Matrix)
```java
@ParameterizedTest @ValueSource(strings={"sqlite","postgres"})
void skuUniquePerTenant(String profile) { /* run with both dialects */ }
```

### 3.3 Python (pytest)
```python
def test_forecast_prophet():
    result = prophet_service.forecast(history=[10,12,15], days=30)
    assert len(result) == 30
    assert result.recommended_po >= 0

def test_anomaly_isolation():
    assert anomaly.check({"total": 999999})["anomaly"] is True
```

### 3.4 Privacy Unit
```java
@Test void privateEntityNeverEnqueues() {
  FinanceLedger ledger = new FinanceLedger(PROFIT, PRIVATE);
  outboxEnqueuer.enqueueIfPublic(ledger);
  assertThat(outboxRepo.count()).isZero();
}
```

---

## 4. Integration & Privacy Auditing

### 4.1 Dual-DB Integration
| Test | SQLite | PG | Expected |
| :--- | :--- | :--- | :--- |
| `SELECT FOR UPDATE` vs `BEGIN IMMEDIATE` | WAL | Row Lock | ACID pass |
| FTS search | MATCH `kopi*` | @@ plainto_tsquery | Same results |
| Flyway migrate | V1_sqlite | V1_postgres | Schema identical |

Config: `@ActiveProfiles("sqlite")` vs `testcontainers: postgres:16`

### 4.2 Privacy Firewall Test (Critical)
```sql
-- Negative test: ensure PRIVATE never in outbox
-- Test inserts Finance PRIVATE then asserts:
SELECT COUNT(*) FROM sync_outbox WHERE table_name='finance_ledger'; -- must be 0
```
Automated in CI: `PrivacyAuditTest` fails build jika ada `sync_policy=PRIVATE` di outbox.

### 4.3 Outbox Reliability
*   Enqueue PUBLIC -> Quartz flush mock Store Cloud (WireMock) -> `synced_at` not null
*   Idempotency replay -> second POST returns 200 without duplicate
*   Offline queue holds 10k rows -> flush saat online batch 50

---

## 5. E2E (TestFX + Playwright)

### 5.1 Desktop E2E (TestFX)
```java
@Test void perfectSaleOffline(FxRobot robot) {
  robot.clickOn("#searchField").write("SKU-001");
  robot.clickOn("#productRow0");
  robot.clickOn("#addToCart");
  robot.clickOn("#payCash");
  robot.clickOn("#confirm");
  verifyThat("#receiptPane", Node::isVisible);
  assertThat(stockRepo.findBySku("SKU-001").getAvailable()).isEqualTo(9);
  assertThat(ledgerRepo.count()).isEqualTo(1);
}
```
Run headless: `mvn test -Dtestfx.headless=true`

### 5.2 Store Cloud E2E (Playwright)
*   Publish product dari Desktop -> `GET /store/api/v1/catalog` contains it
*   Customer order via Playwright -> Desktop pull sync -> order appears in local `sales`

---

## 6. Performance & Load

| Metric | Threshold | Tool |
| :--- | :--- | :--- |
| POS checkout | <50ms (SQLite/PG lokal) | JMH + JUnit |
| FTS 100k SKU search | <100ms | JMH |
| Python forecast 90d | <2s | pytest-benchmark |
| Store Cloud 500 concurrent publish | P95 <200ms | k6 |
| Installer startup | <3s | manual |

**k6 Example (Store Cloud)**
```js
http.post('https://store.sosha.com/api/v1/stock/publish', JSON.stringify({sku, qty}), {headers:{'X-Tenant-Api-Key':key}})
```

**SQLite vs PG Benchmark**
*   SQLite: 1 kasir, 100k SKU -> checkout 35ms avg
*   PG lokal: 10 kasir concurrent -> 45ms avg (row lock scale)

---

## 7. UAT (Offline Scenarios)

| Role | Scenario |
| :--- | :--- |
| Cashier | 7 hari offline, 500 transaksi, lalu online sync -> no loss |
| Manager | Pilih SQLite di laptop 2GB -> install success |
| IT Admin | Migrasi SQLite->PG 50k produk -> verify checksum |
| Owner | Cek `~/.sosha/logs/sync.log` -> 0 PRIVATE payload |

---

## 8. Security (Desktop)

1.  **SQLCipher:** Verify `sosha.db` tidak bisa dibuka tanpa key (hexdump)
2.  **BCrypt:** Login dengan salah 5x -> lock 5 min
3.  **JWT:** Expired token -> redirect login
4.  **File Permission:** `~/.sosha/` chmod 700

---

## 9. CI Integration

```yaml
jobs:
  test:
    strategy: {matrix: {profile: [sqlite, postgres]}}
    steps:
      - run: mvn test -Dspring.profiles.active=${{matrix.profile}}
      - run: mvn -pl python-sidecar test
      - run: mvn testfx:test -Dheadless
      - name: Privacy Audit
        run: mvn test -Dtest=PrivacyAuditTest
      - name: k6 Store Cloud
        if: matrix.profile == 'postgres'
        run: k6 run tests/k6/publish.js
```

Coverage: JaCoCo 80% Java, `coverage.py` 80% Python.

---

## 10. Best Practices

1.  Deterministic seed `V999__seed_test.sql` untuk kedua DB
2.  Mock Python sidecar via WireMock `:8001` saat Java test, mock Store Cloud `:8081`
3.  Visual regression: TestFX snapshot compare untuk receipt layout
4.  Never test against production Store Cloud; use ephemeral PG Testcontainer

## 11. Risks

| Risk | Mitigasi |
| :--- | :--- |
| Flaky TestFX | Retry 3 + `WaitForAsyncUtils` |
| Dual-DB divergence | Matrix CI fail fast |
| Privacy leak | Annotation processor + audit test blocking |

## 12. Checklist
- [ ] JaCoCo 80% + privacy audit pass
- [ ] TestFX headless pass
- [ ] Matrix sqlite/postgres green
- [ ] k6 P95 <200ms
- [ ] `sosha.db` encrypted verified

**End of Volume 9 v2.0**
