# SESSION 00 - Project Bootstrap & Dual-DB

**Target:** Inisialisasi monorepo Java+Python, dual-DB abstraction siap.

## Tujuan
- Maven multi-module (`desktop`, `python-sidecar`, `store-cloud`)
- Java 21 + Spring Boot 3.3 + JavaFX 21 + Hibernate + Flyway
- Dual-DB: `application-sqlite.yml` (SQLite+SQLCipher, Hikari pool 1, SQLiteDialect) & `application-postgres.yml` (PG16, pool 20)
- Python FastAPI skeleton `:8001/health`

## Task
- [ ] `mvn archetype` + `pom.xml` parent
- [ ] `SoshaApp extends Application` + `SpringApplicationBuilder`
- [ ] `DataSourceConfig` profile-based + `SQLiteDialect` custom
- [ ] Flyway `common/` + `sqlite/` + `postgres/` migrations
- [ ] `python-sidecar/app/main.py` FastAPI

## Deliverable
- `mvn verify -Psqlite` & `-Ppostgres` green
- `mvn javafx:run` muncul window kosong

## Report
-> `Report_code_execute/REPORT_SESSION_00.md`
