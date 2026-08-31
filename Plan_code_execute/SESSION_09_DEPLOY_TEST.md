# SESSION 09 - Installer jpackage + Testing Matrix

## Tujuan
Build native installer 3 OS + matrix testing Dual-DB + privacy audit.

## Scope
- jpackage MSI/DEB/DMG, Update4j, Store Cloud Docker
- JaCoCo 80%, TestFX, pytest, k6

## Task
1. Maven `jlink` + `jpackage` config per OS runner (Win/Ubuntu/macOS)
2. Update4j `update.xml` signed, hosted `https://store.sosha.com/updates/`
3. `store-cloud/Dockerfile` + `docker-compose.yml` + Caddy TLS
4. Testing: `mvn test -Psqlite` & `-Ppostgres` matrix, `PrivacyAuditTest`, TestFX headless, `pytest`, k6 `tests/k6/publish.js`
5. Benchmark: JMH POS <50ms, FTS 100k <100ms
6. Verify: `sosha.db` encrypted, install 2GB VM SQLite pass
7. Docs: `README` install guide, `CHANGELOG`

## Deliverable
- 3 installers (~130MB) di GitHub Release draft
- CI green both profiles + privacy audit pass
- k6 P95 <200ms
- JaCoCo 80%

## File
- `pom.xml` jpackage, `.github/workflows/ci.yml`, `tests/k6/publish.js`, `store-cloud/Dockerfile`

## Kriteria Banding (final)
- Installer size ~130MB
- Startup <3s
- Both DB green
- Privacy 0 leak

## Estimasi: 5 hari
