# SESSION 04 - POS Offline <50ms

## Tujuan
Kasir checkout offline super cepat tanpa internet, akurasi stok ACID.

## Scope
- `Sale`, `SaleItem`, `Payment` entities (idempotencyKey UNIQUE)
- `PricingService` (Standard, Tiered, FlashSale Strategy), `SaleService.checkout()` @Transactional
- `CartViewModel` ObservableList + `PosController` JavaFX
- ESC/POS print stub

## Task Detail
1. Entities: `sales(id, tenant_id, branch_id, customer_id, total, tax, payment_status, idempotency_key UNIQUE, sync_policy)`, `sale_items`, `payments`
2. `PricingService`: BigDecimal, strategy pattern, tax per branch, discount tier
3. `SaleService.checkout(cmd)`: validate stock -> deduct -> ledger -> save sale -> enqueue outbox if items PUBLISHED -> publish SaleCompletedEvent
4. `CartViewModel`: add/remove, total property, hold/resume via `carts` table
5. JavaFX `PosController`: FXML SplitPane Search left + Cart right, hotkeys F9 Pay, F2 Search, HID barcode KeyEvent
6. `ReceiptPrinterService`: jSerialComm byte stream (mock jika no printer)

## Deliverable
- Checkout 5 item <50ms (JMH)
- Idempotency replay return same saleId
- Hold/resume cart persists

## File
- `core/domain/Sale.java`, `SaleItem.java`, `core/service/PricingService.java`, `SaleService.java`, `ui/pos/PosController.java`, `ui/pos/CartViewModel.java`

## Kriteria Banding
- Checkout latency <50ms both DB
- Stock decrement + ledger atomic
- 409 insufficient stock

## Estimasi: 6 hari
