# Plan Code Execute - Sosha POS v2.0 (Java + Python Offline-First)

> **Stack:** Java 21 + JavaFX + Spring Boot Embedded + SQLite/PostgreSQL (Dual-DB) + Python FastAPI sidecar + Store Cloud

## Daftar Session

| Session | File | Fokus | Status |
| :--- | :--- | :--- | :--- |
| 00 | `SESSION_00_SETUP.md` | Project bootstrap & Dual-DB abstraction | pending |
| 01 | `SESSION_01_AUTH.md` | Auth lokal, RBAC, TenantFilter | pending |
| 02 | `SESSION_02_CATALOG.md` | Catalog + FTS (SQLite FTS5 / PG GIN) | pending |
| 03 | `SESSION_03_INVENTORY.md` | Inventory, StockLedger, FEFO | pending |
| 04 | `SESSION_04_POS.md` | POS Offline <50ms | pending |
| 05 | `SESSION_05_SYNC.md` | Outbox + Store Cloud selective sync | pending |
| 06 | `SESSION_06_FINANCE.md` | Finance PRIVATE + AuditLog | pending |
| 07 | `SESSION_07_UI.md` | JavaFX UI System + Hardware | pending |
| 08 | `SESSION_08_AI.md` | Python sidecar (Prophet, Anomaly, RAG) | pending |
| 09 | `SESSION_09_DEPLOY_TEST.md` | Installer jpackage + Testing matrix | pending |

## Cara Pakai
1. Kerjakan per session berurutan (00 -> 09).
2. Setiap session selesai, buat laporan di `Report_code_execute/REPORT_SESSION_XX.md` pakai template.
3. Update status di tabel atas.

## Aturan Dual-DB
Semua code wajib lolos `mvn test -Psqlite` dan `mvn test -Ppostgres`. Jangan pakai SQL native tanpa dialect fallback.

## Privacy Rule
Entity `PRIVATE` (`FinanceLedger`, `Payroll`) tidak pernah `enqueue` ke `sync_outbox`. Audit via `PrivacyAuditTest`.
