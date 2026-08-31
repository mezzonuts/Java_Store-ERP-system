# REPORT SESSION 02 - Catalog & FTS Search

## 1. Tujuan Code Dibuat
Master produk dengan FTS cepat <100ms di kedua DB, support barcode, UOM, kategori, async search UI.

## 2. Bug Tracker / Catatan Kesalahan
- Tidak ada error compile — skeleton sukses  
- ProductRepository `@Query` native SQL belum dialect-aware (TODO: branch SQLite vs PG)

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas | Catatan Kunci |
| :--- | :--- | :--- | :--- |
| `ProductRepository.search()` | LIKE pattern match | O(n) | Native SQL, belum FTS optimal |
| FTS5 trigger (SQLite) | Virtual table + trigger | O(log n) insert | Sync index on-the-fly |
| GIN index (PG) | B-Tree + GIN bitmap | O(log n) | Full-text search via tsvector |
| `CatalogController.search()` | TextProperty listener + TableView | O(n*m) | n=products, m=visible rows |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- `domain/Product.java`, `Category.java`
- `repository/ProductRepository.java`
- `service/ProductService.java`
- `sync/OutboxEnqueuer.java` (stub)
- `ui/CatalogController.java`
- `resources/fxml/catalog.fxml`
- `resources/db/migration/sqlite/V2__fts.sql`
- `resources/db/migration/postgres/V2__fts.sql`

**Compile:** ✅ 18 class files (from 11 prev)  
**Test:** manual (belum unit test)  

## 5. Selanjutnya
SESSION 03: Inventory, StockLevel, ACID transaksi, FEFO expiry, stock ledger.
