# REPORT SESSION 05 - Outbox & Store Cloud Selective Sync

## 1. Tujuan Code Dibuat
Sync selektif PUBLIC data ke Store Cloud saat online, PRIVATE never sync, reliable dengan retry & idempotency.

## 2. Bug Tracker / Catatan Kesalahan
- **SyncOutbox missing setters** → Added getters/setters for all fields
- `StoreClient.publish()` still stub → will implement Retrofit in next session
- No retry backoff strategy yet → exponential backoff should be added

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas | Catatan |
| :--- | :--- | :--- | :--- |
| `OutboxEnqueuer.enqueue()` | INSERT sync_outbox | O(1) | Only called if PUBLIC + is_published |
| `SyncScheduler.flushOutbox()` | Batch polling + retry 3x | O(n*m) | n=pending, m=retry |
| `StoreClient.publish()` | HTTP POST stub | - | Retrofit to `https://store.sosha.com/api/v1/publish` |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- `domain/SyncOutbox.java` (10 fields)
- `repository/SyncOutboxRepository.java`
- `sync/OutboxEnqueuer.java`, `SyncScheduler.java`, `StoreClient.java`
- Updated `SoshaSpringApp.java` (`@EnableScheduling`)

**Compile:** ✅ 37 class files (prev 33)  
**Test:** manual (sync scheduler runs every 5s)  

## 5. Selanjutnya
SESSION 06 – Finance PRIVATE + AuditLog (encrypted, no sync, full audit trail).
