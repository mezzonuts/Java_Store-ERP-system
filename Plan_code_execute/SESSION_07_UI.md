# SESSION 07 - JavaFX UI System & Hardware

## Tujuan
Shell desktop polished: nav, theme, virtualized tables, hardware ESC/POS & HID.

## Scope
- `MainController` shell, `SettingsController` (DB wizard, theme), shared components
- `DataTable` virtualized, `SearchField` FTS, `NotificationPane`
- Hardware: HID scanner, ESC/POS jSerialComm, ZPL label

## Task
1. `MainController` BorderPane: Top (branch, sync dot, user), Left Nav (POS, Catalog, Inventory, Finance, Settings), Center content lazy load
2. `DbSelectorController` wizard: detect RAM/CPU recommend SQLite/PG, set profile, Flyway migrate
3. Shared: `DataTable<T>` pagination 50, `FilteredList` debounce 150ms, `ValidationSupport`
4. Theme: `light.css` / `dark.css` switch via `scene.getStylesheets()`, persisted
5. Hardware: `BarcodeScannerService` KeyEvent buffer (HID), `ReceiptPrinterService` ESC/POS bytes, `LabelService` ZPL
6. i18n `messages.properties` (ID/EN)

## Deliverable
- All tabs navigable, 100k row table smooth 60fps
- Dark/light toggle
- Scan barcode -> auto add to cart
- Print receipt to file/mock

## File
- `ui/MainController.java`, `ui/common/DataTable.java`, `SearchField.java`, `ui/settings/DbSelectorController.java`, `hardware/PrinterService.java`

## Kriteria
- Virtualized 60fps
- Hotkeys F9/F2 work
- Print bytes correct (GS V)

## Estimasi: 5 hari
