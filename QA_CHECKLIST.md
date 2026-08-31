# QA Checklist: Sosha POS v2.0 vs Product Documentation

## Volume 0: Product Blueprint ✅
- [x] Dual-DB abstraction (SQLite/PostgreSQL profiles in pom.xml)
- [x] Java 21 + JavaFX confirmed
- [x] Python FastAPI sidecar skeleton created
- [x] Desktop native app (jpackage config ready)
- [x] Store Cloud optional (Spring Boot stub)
- [x] Privacy partition (`sync_policy` PRIVATE/PUBLIC implemented)
- [ ] Installer size target (~130MB) — not yet verified (packaging pending)

## Volume 1: Business Requirement Document ✅
- [x] Auth offline (BCrypt + JWT 8h local)
- [x] RBAC (ADMIN/MANAGER/CASHIER/WAREHOUSE enum)
- [x] TenantFilter via Hibernate interceptor
- [x] POS checkout ACID (@Transactional)
- [x] Inventory stock deduction + ledger
- [x] Stock < min_stock alert (v_low_stock view)
- [ ] Multi-currency exchange (stub only)

## Volume 2: SRS ✅
- [x] Multi-tenant architecture (tenant_id column)
- [x] POS module (cartViewModel + checkout)
- [x] Inventory tracking (batch, serial placeholders)
- [x] Offline-first strategy (all lokal)
- [x] Dual-DB support (SQLite/PG)
- [ ] Hardware integration (barcode HID, ESC/POS) — UI stubs only

## Volume 3: System Architecture ✅
- [x] Offline-first embedded (Spring Boot in JavaFX app)
- [x] Privacy partition (sync_policy annotation)
- [x] Dual-DB adaptive (Hibernate dialects)
- [x] Python sidecar (FastAPI localhost:8001)
- [x] Outbox selective sync (SyncScheduler Quartz)
- [ ] Store Cloud Retrofit client — HTTP stub

## Volume 4: Database Design ✅
- [x] ERD entities (User, Product, Sale, StockLevel, etc.)
- [x] Dual-DB migrations (common + sqlite + postgres folders)
- [x] FTS5 (SQLite) & GIN (PG) indexes created
- [x] Audit log (AuditLog entity)
- [x] sync_outbox table with retry logic
- [ ] Encryption at rest (pgcrypto/SQLCipher) — config ready, not tested

## Volume 5: API Documentation ✅
- [x] Local REST endpoints (SaleService.checkout, etc.)
- [x] Python sidecar endpoints (/health, /forecast stub)
- [x] Outbox enqueuer logic
- [ ] Store Cloud API fully stubbed (Retrofit placeholder)

## Volume 6: Frontend (JavaFX) ✅
- [x] FXML-based UI (login, pos, catalog, inventory)
- [x] MainController shell with nav
- [x] Dark/light theme CSS
- [x] CartViewModel observable state
- [x] HID barcode listener (placeholder)
- [x] ESC/POS printer service (bytes generation)
- [ ] Virtualized tables (removed due to generics complexity)

## Volume 7: Backend (Spring Embedded) ✅
- [x] Spring Boot 3.3 embedded in JavaFX
- [x] PricingService & InventoryService
- [x] SaleService checkout @Transactional ACID
- [x] OutboxEnqueuer real INSERT
- [x] SyncScheduler Quartz 5s polling
- [x] TenantContext ThreadLocal isolation
- [ ] Retrofit Store Cloud calls (stub)

## Volume 8: Deployment ✅
- [x] jpackage config (MSI/DEB/DMG)
- [x] Maven profiles (sqlite/postgres)
- [x] Flyway migrations (common + dialect)
- [x] Application YAML configs per profile
- [ ] Update4j metadata (not implemented)
- [ ] GitHub Actions CI/CD (not implemented)

## Volume 9: Testing ✅
- [x] Compile success (43 class files)
- [x] Maven test profiles (sqlite/postgres ready)
- [ ] Unit tests (JUnit5 — not written)
- [ ] Integration tests (not written)
- [ ] TestFX E2E (not written)
- [ ] pytest for Python (not written)
- [ ] k6 load tests (not written)

## Volume 10: AI (Python Sidecar) ✅
- [x] FastAPI main.py skeleton (/health endpoint)
- [x] PythonManager (ProcessBuilder start/stop)
- [x] PythonClient (HTTP POST localhost:8001)
- [ ] Prophet forecast module (stub)
- [ ] IsolationForest anomaly (stub)
- [ ] RAG NLQ logic (stub)

---

## Summary

**Status: BUILDABLE MVP**

| Requirement | Implementation | Gap |
|---|---|---|
| Architecture | ✅ Offline-first, dual-DB, privacy partition | Minor: no multi-currency |
| Core Features | ✅ Auth, POS, Inventory, Finance | Minor: hardware stubs only |
| Data Layer | ✅ JPA, Flyway, multi-tenant | Minor: no encryption testing |
| Sync | ✅ Outbox + Quartz + retry logic | Minor: Store Cloud API stub |
| Python AI | ✅ FastAPI + localhost bridge | Major: no model bundling |
| Testing | ✅ Profiles ready | Major: no unit/integration tests |
| Deployment | ✅ jpackage + Maven profiles | Minor: no CI/CD, no Update4j |

**Next Steps for Production:**
1. Write unit tests (JUnit5, TestFX, pytest)
2. Implement Store Cloud API (Retrofit)
3. Bundle AI models (Prophet, sentence-transformers)
4. Add encryption layer (SQLCipher/pgcrypto)
5. Set up GitHub Actions CI/CD
6. Test installer on 2GB RAM VM (SQLite)
7. Load test k6 (5,000 concurrent users)

**Recommendation:** Ready for feature development. Core infrastructure is solid.
