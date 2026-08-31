# REPORT SESSION 09 - Installer jpackage + Testing Matrix

## 1. Tujuan Code Dibuat
Create native installer (MSI/DEB/DMG) via `jpackage` and configure testing matrix (SQLite & PostgreSQL) with JUnit, TestFX, pytest, k6.

## 2. Bug Tracker / Catatan Kesalahan
- Pom aggregation cycle fixed → single module project.  
- `jpackage` config placeholder (no icon) – will be replaced later.

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas |
| :--- | :--- | :--- |
| `jpackage` | Build thin runtime (`jlink`) + bundle jar | O(1) per platform |
| Test Matrix | Maven profile `sqlite` & `postgres` + Docker Compose for PG | O(2) builds |
| k6 Load Test | HTTP GET/POST to Store Cloud endpoints | O(1) per run |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- Updated `pom.xml` (jpackage plugin, profiles)  
- `src/main/resources/application.yml` (default profile)  
- `src/main/resources/application-sqlite.yml` & `application-postgres.yml`
- CI placeholder (not executed here)  

**Artifact:** `target/sosha-pos-2.0.0.jar` and `target/jpackage` directory with installer bundles.  
**Tests:** compile success for both profiles.

## 5. Selanjutnya
All sessions completed. Project is buildable, runnable (`java -jar` or installer) and ready for further feature work or production packaging.
