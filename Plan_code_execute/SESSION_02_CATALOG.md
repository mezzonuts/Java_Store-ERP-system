# SESSION 02 - Catalog & FTS Search

## Tujuan
Master produk dengan FTS cepat <100ms di kedua DB, support barcode, UOM, variant.

## Scope
- `Product`, `Category`, `Uom` entities (sync_policy PUBLIC/PRIVATE, is_published)
- `ProductRepository` + FTS abstraction
- Flyway: `products_fts` (SQLite FTS5) vs GIN (PG)
- JavaFX `CatalogController` + `SearchField`

## Task Detail
1. Entity `products` + `categories` + `uoms` + indexes
2. FTS: SQLite `CREATE VIRTUAL TABLE products_fts USING fts5(name,sku)` + triggers; PG `GIN(to_tsvector)`
3. Repository `search(String q, Pageable)` branch dialect via `@Query(nativeQuery)` + profile
4. Service `ProductService.create/update` + `outboxEnqueueIfPublic`
5. Barcode validation EAN-13 checksum
6. UI: TableView virtualized 50/page, autocomplete

## Deliverable
- Search "kopi*" <100ms di 100k SKU (both DB)
- Create product PUBLIC auto enqueue outbox, PRIVATE tidak

## File
- `core/domain/Product.java`, `core/repository/ProductRepository.java`, `core/service/ProductService.java`, `ui/CatalogController.java`

## Kriteria Banding
- FTS result identical SQLite/PG
- Privacy test: PRIVATE count 0 in outbox

## Estimasi: 4 hari
