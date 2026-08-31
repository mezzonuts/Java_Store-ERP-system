# REPORT SESSION 03 - Inventory, StockLedger & FEFO

## 1. Tujuan Code Dibuat
Manajemen stok ACID lokal multi-warehouse dengan audit ledger, stock level tracking.

## 2. Bug Tracker / Catatan Kesalahan
- Tidak ada error compile — skeleton sukses  
- `InventoryService` belum pakai `SELECT FOR UPDATE` (SQLite: BEGIN IMMEDIATE, PG: FOR UPDATE)  
- Batch & Serial table belum linked ke Product  

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas | Catatan Kunci |
| :--- | :--- | :--- | :--- |
| `InventoryService.adjustStock()` | @Transactional CRUD | O(1) | JPA merge + save |
| `StockLevel` | Optimistic lock | O(1) | @Version field |
| SQLite concurrency | BEGIN IMMEDIATE + WAL | Serial | maxPool=1 enforced |
| PG concurrency | SELECT FOR UPDATE | Row-level lock | Wait queue |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- `domain/Warehouse.java`, `StockLevel.java`, `StockLedger.java`, `Batch.java`
- `repository/StockLevelRepository.java`, `StockLedgerRepository.java`
- `service/InventoryService.java`
- `ui/InventoryController.java`
- `resources/fxml/inventory.fxml`
- `db/migration/sqlite/V3__inventory.sql`, `postgres/V3__inventory.sql`

**Compile:** ✅ 26 class files (prev: 18)  
**Test:** manual (belum unit test)  

## 5. Selanjutnya
SESSION 04: POS Offline <50ms, Sale entities, Pricing Engine Strategy,Checkout ACID.
