## SOSHA POS & INVENTORY MANAGEMENT SYSTEM VOLUME 1: BUSINESS REQUIREMENT DOCUMENT (BRD)
### Strategic Business Analysis & Requirement Definition - v2.0 Java+Python Offline-First

---

## 1. Executive Summary
**Sosha v2** adalah **Desktop Offline-First ERP** (Java 21 + Python 3.11) dengan **Store Online** opsional. Semua operasional kritis (POS, Inventory, Finance, HR) berjalan 100% offline di device user dengan DB lokal **SQLite atau PostgreSQL** (pilih sesuai spek). Hanya data publik toko (katalog publish, harga, order) yang selective-sync ke cloud saat online. Data administrasi privat terenkripsi AES-256 dan **never sync**.

---

## 2. Current Problems Solved

1.  **Cloud Dependency:** Toko tidak bisa jualan saat internet mati -> Solved: Offline ACID lokal.
2.  **Data Privacy:** Laporan laba/HPP bocor di cloud -> Solved: `sync_policy=PRIVATE` never sync.
3.  **Spec Rigidity:** Satu DB tidak cocok semua spek -> Solved: Dual-DB (SQLite ringan, PG scalable).
4.  **Store Tetap Online:** Pelanggan tetap butuh belanja online -> Solved: Store Cloud selective sync.
5.  **AI Mahal & Online:** Forecast butuh cloud -> Solved: Python sidecar lokal offline.

---

## 3. Business Objectives

| ID | Objective | Impact | KPI |
| :--- | :--- | :--- | :--- |
| **OBJ-01** | Unified Offline Inventory | Stok real-time lokal | 0% oversell offline |
| **OBJ-02** | Privacy Partition | Data privat tidak ke cloud | 0 leak audit |
| **OBJ-03** | Adaptive DB | Jalan di spek 2GB-64GB | Install success 99% |
| **OBJ-04** | Store Online Selective | E-commerce tetap live | Sync <2s saat online |
| **OBJ-05** | Local AI Automation | Forecast tanpa internet | 80% PO auto lokal |

---

## 4. Business Process Modeling

### 4.1 Procurement to Inventory (Offline Inbound)
1.  AI Python prediksi stok tipis (lokal) -> Draft PO
2.  Manager approve offline -> PO tercatat lokal (PRIVATE)
3.  Barang datang -> Scan batch/serial lokal -> Stock entry ACID -> Ledger lokal
4.  Jika `publish_to_store=true`, outbox push stok publish ke cloud saat online

### 4.2 Sales to Fulfillment (Offline Outbound)
1.  Cashier scan di JavaFX POS (<50ms lokal)
2.  Cek stok lokal (SQLite/PG), apply discount/tax (Java)
3.  Payment lokal (cash/card offline) -> Deduct stok ACID lokal
4.  Print ESC/POS lokal -> Jika barang publish, stok publish sync ke store cloud via outbox

### 4.3 Online Order to Desktop
1.  Customer order via Store Web (Cloud PG)
2.  Desktop pull sync saat online -> Order masuk antrean lokal
3.  Gudang proses offline -> Update status sync balik ke cloud

---

## 5. Business Rules (Updated)

| Rule ID | Category | Rule Definition |
| :--- | :--- | :--- |
| **BR-01** | Privacy Partition | Tabel/kolom dengan `sync_policy=PRIVATE` tidak pernah masuk `sync_outbox`. Enforced via DB trigger + code review + test. |
| **BR-02** | Dual-DB | User pilih DB saat install; migrasi SQLite->PG didukung via migrator tool. Hibernate dialect menangani perbedaan SQL. |
| **BR-03** | Offline Stock | `is_tracked` item tidak bisa minus kecuali `allow_negative=true`; validasi via `CHECK` constraint di SQLite & PG. |
| **BR-04** | Currency | Transaksi simpan `local_currency` & `base_currency` lokal; kurs dari Python sidecar (offline cache ECB). |
| **BR-05** | Returns | Harus refer `original_sale_id`, grace period configurable, validasi stok lokal. |
| **BR-06** | Batching | FEFO default; expired alert dari Python anomaly lokal. |
| **BR-07** | Sync Selectivity | Hanya `is_published=true` + `sync_policy=PUBLIC` yang sync ke Store Cloud. |

---

## 6. Functional Requirements

### 6.1 Core POS (Offline)
*   **FR-1.1:** Scan Barcode EAN-13/UPC via HID, lookup <50ms dari lokal DB (FTS5 SQLite / GIN PG)
*   **FR-1.2:** Suspend/Resume cart disimpan lokal (JSON file + DB)
*   **FR-1.3:** Multi-payment split offline
*   **FR-1.4:** 100% offline, sync outbox saat online

### 6.2 Inventory & Warehouse (Offline)
*   **FR-2.1:** Multi-warehouse transfer lokal
*   **FR-2.2:** Stock adjustment dengan reason code -> `stock_ledger`
*   **FR-2.3/2.4:** Serial/Batch/Expiry tracking lokal

### 6.3 CRM & Loyalty (Offline Private)
*   Data member lokal PRIVATE, loyalty point lokal; tidak sync kecuali `allow_sync_member=false` default

### 6.4 Store Online (Cloud Public)
*   **FR-4.1:** Katalog publish, stok publish, harga publish sync ke cloud PG
*   **FR-4.2:** Order online pull ke desktop

---

## 7. Non-Functional Requirements

| Category | Requirement | Specification (v2) |
| :--- | :--- | :--- |
| **Performance** | POS latency | <50ms lokal (baik SQLite WAL maupun PG) |
| **Availability** | Offline | 100% fungsional 30 hari tanpa internet |
| **Security** | At Rest | SQLite: SQLCipher AES-256, PG: pgcrypto + FS encrypt, BCrypt password |
| **Scalability** | DB Choice | SQLite: 0-100k SKU, 1-3 kasir; PG: 100k-5M SKU, 10+ kasir concurrent |
| **Privacy** | No Leak | Audit `sync_outbox` tidak mengandung PRIVATE data |
| **Audit** | Traceability | Trigger `updated_at` + `audit_log` lokal |

---

## 8. User Stories (Updated)

| As a... | I want to... | So that... |
| :--- | :--- | :--- |
| **Owner** | Pilih DB SQLite untuk laptop kentang | Tetap lancar tanpa upgrade |
| **Owner** | Laporan laba tidak sync ke cloud | Rahasia keuangan aman |
| **Cashier** | POS tetap jalan saat WiFi mati | Penjualan tidak berhenti |
| **Manager** | Stok publish otomatis ke toko online saat online | Pelanggan online lihat stok akurat |
| **IT Admin** | Migrasi SQLite ke PG saat scale | Tanpa reinstall data |

---

## 9. Use Case Diagram
```mermaid
usecaseDiagram
    actor "Cashier Offline" as staff
    actor "Manager Offline" as manager
    actor "Customer Online" as cust
    package "Sosha Desktop (JavaFX+LocalDB)" {
        staff --> (Process Sale Offline)
        staff --> (Check Stock Local)
        manager --> (Inventory Audit Local)
        manager --> (View Finance PRIVATE Local)
        manager --> (Publish Catalog to Store)
    }
    package "Sosha Store Cloud" {
        cust --> (Browse Catalog)
        cust --> (Place Order)
    }
    (Publish Catalog to Store) --> (Browse Catalog)
    (Place Order) --> (Inventory Audit Local) : pull sync
```

---

## 10. Activity Diagram: Dual-DB Selection
```mermaid
activityDiagram
    start
    :User Install Sosha Desktop;
    if (RAM < 4GB or SKU < 100k?) then (yes)
        :Pilih SQLite (WAL, single file);
    else (yes)
        :Pilih PostgreSQL Lokal;
        :Install PG 16 embedded / external;
    endif
    :Flyway migrasi dialect-aware;
    :App jalan ACID lokal;
    if (Internet ON?) then (yes)
        :Outbox push PUBLIC data ke Store Cloud;
        :Pull Order Online;
    endif
    stop
```

---

## 11. Sequence: POS Offline + Selective Sync
```mermaid
sequenceDiagram
    participant C as Cashier
    participant FX as JavaFX
    participant DB as Local DB (SQLite/PG)
    participant OB as Outbox
    participant SC as Store Cloud

    C->>FX: Scan Item
    FX->>DB: Query stock & price (<50ms)
    DB-->>FX: Return
    C->>FX: Pay Cash Offline
    FX->>DB: BEGIN; Insert sale; Decrement stock; COMMIT
    FX->>OB: If is_published then enqueue stock delta
    FX->>C: Print Receipt ESC/POS
    Note over OB,SC: Saat Online
    OB->>SC: POST /store/api/publish-stock (retrofit)
    SC-->>OB: 200 OK
    OB->>OB: Mark synced
```

---

## 12. Acceptance Criteria

1.  Install wizard menampilkan pilihan SQLite/PG; kedua opsi lolos test 100k SKU.
2.  Matikan internet 7 hari: semua POS & laporan tetap jalan, `sync_outbox` tertahan.
3.  `SELECT * FROM sync_outbox JOIN products WHERE sync_policy='PRIVATE'` harus 0 row.
4.  Migrasi SQLite->PG memindahkan 50k produk tanpa loss.
5.  Store online menampilkan hanya produk `is_published=true`.

---

## 13. Best Practices

*   **Soft Deletes:** `deleted_at` timestamp
*   **Constraints:** `CHECK (base_price >=0)`, FK RESTRICT
*   **Idempotency Key:** UUID untuk sale & outbox deduplication
*   **DB Abstraction:** Jangan pakai `RETURNING` mentah (PG) tanpa fallback; gunakan JPA

**End of Volume 1 v2.0**

**Architect Confirmation:** *BRD v2 mendefinisikan bisnis offline-first dengan privasi dan skalabilitas via Dual-DB.*
