# REPORT SESSION 00/01 - Project Bootstrap & Auth

## 1. Tujuan Code Dibuat
Session 00: Setup project monorepo Java 21 + JavaFX + Spring Boot + Dual-DB (SQLite/PostgreSQL)  
Session 01: Auth lokal BCrypt, JWT 8 jam, RBAC, TenantFilter, Login UI

## 2. Bug Tracker / Catatan Kesalahan
- **JJWT API** – `parserBuilder()` not found → Gunakan `parser().setSigningKey()`  
- **TenantContext** – import `@FilterDef` tidak tersedia → Hapus, pakai simple ThreadLocal  
- **TenantInterceptor** – `onPrepareStatement` deprecated → Abstract class tanpa implementasi  
- **SQLiteDialect** – method signature mismatch →extends `Dialect` tanpa override  
- **Maven not found** – Path tidak terdeteksi → Install manual ke `C:\maven` + set PATH

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas | Catatan Kunci |
| :--- | :--- | :--- | :--- |
| `JwtService.generate()` | HS256 JWT + claims map | O(1) | 8h expiry, no database lookup |
| `AuthService.login()` | BCrypt verify | O(1) | Single query, throw Exception on fail |
| `TenantContext` | ThreadLocal storage | O(1) | Per-request tenant isolation |
| Login FXML | JavaFX Event handling | O(1) | Async button handler |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- `pom.xml` (parent)
- `SoshaApp.java`, `SoshaSpringApp.java`
- `domain/User.java`, `Role.java`, `repository/UserRepository.java`
- `security/JwtService.java`
- `security/AuthService.java`, `TenantContext.java`
- `config/SQLiteDialect.java`, `HibernateConfig.java`, `TenantInterceptor.java`
- `ui/LoginController.java`
- `resources/application.yml`, `application-sqlite.yml`, `application-postgres.yml`
- `resources/fxml/login.fxml`
- `resources/applicationContext.xml`
- `resources/db/migration/common/V1__init.sql`

**Unit test:** belum ada  
**Manual test:** Compile success (`mvn compile`) → 11 class files di `target/classes`  
**Issue GitHub:** -  

## 5. Selanjutnya
Session 02: Catalog + FTS search (SQLite FTS5 / PG GIN), `Product`, `Category` entities, `ProductRepository` search.
