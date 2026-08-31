# REPORT SESSION 04 - POS Offline \u003c50ms Checkout

## 1. Tujuan Code Dibuat
Implementasi POS UI JavaFX dengan checkout ACID <50ms, cart ViewModel, barcode scan, total kalkulasi.

## 2. Bug Tracker / Catatan Kesalahan
- `CartViewModel` not thread‑safe (but used only on FX thread) – acceptable.
- `PosController` hard‑coded unit price – placeholder, real price fetched from ProductRepo later.
- No persistence of cart (in‑memory only) – will be added in later session (hold/resume).

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas |
| :--- | :--- | :--- |
| `CartViewModel.recalc()` | Sum qty*price over list | O(n) per add/remove |
| `PosController.onScan()` | Create `SaleItem` (id UUID) & add to cart | O(1) |
| `PosController.onPay()` | Compute total from ViewModel (already computed) | O(1) |
| Checkout (future `SaleService.checkout`) | @Transactional stock deduction + sale save | O(k) where k = items count |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- `domain/Payment.java`, `Sale.java`, `SaleItem.java`
- `repository/SaleRepository.java`
- `service/SaleService.java`
- `ui/pos/CartViewModel.java`, `PosController.java`
- `resources/fxml/pos.fxml`
- `ui/pos` package structure

**Compile:** ✅ 33 class files (prev 26).  
**Test:** manual UI launch (no unit tests yet).  
**Issue:** price lookup placeholder – to be wired to `ProductService` in next session.

## 5. Selanjutnya
SESSION 05 – Outbox & Selective Sync (Store Cloud). Implement `OutboxEnqueuer` real insert, `SyncScheduler` Quartz, `StoreClient` Retrofit.
