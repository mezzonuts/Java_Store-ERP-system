# SESSION 03 - Inventory, StockLedger & FEFO

## Tujuan
Manajemen stok ACID lokal multi-warehouse dengan audit ledger dan FEFO expiry.

## Scope
- `Warehouse`, `StockLevel`, `StockLedger`, `Batch`, `Serial` entities
- `InventoryService` @Transactional dengan `SELECT FOR UPDATE` (PG) / `BEGIN IMMEDIATE` (SQLite)
- FEFO picking, min_stock alert
- `v_low_stock` view

## Task Detail
1. Entities: `warehouses`, `stock_levels(product_id, warehouse_id, available, reserved, min_level)`, `stock_ledger`, `batches(expiry)`, `serials`
2. Service: `adjustStock()`, `transfer()`, `reserve()`, `release()` - semua @Transactional + ledger insert + outbox if PUBLIC
3. Concurrency: PG `findForUpdate`, SQLite `maxPool=1` + `BEGIN IMMEDIATE` via `@Query("BEGIN IMMEDIATE")` wrapper
4. FEFO: `findBatchesOrderByExpiry()` untuk picking
5. View `v_low_stock` (common SQL)
6. UI `InventoryController` adjustment dialog + reason code

## Deliverable
- Transfer 50 unit antar warehouse ACID, ledger tercatat
- Concurrent checkout 2 kasir last item -> satu gagal 409
- `v_low_stock` tampil alert

## File
- `core/domain/StockLevel.java`, `StockLedger.java`, `Batch.java`, `core/service/InventoryService.java`, `ui/InventoryController.java`

## Kriteria Banding
- ACID green both DB
- Ledger count == movement count
- FEFO order correct

## Estimasi: 5 hari
