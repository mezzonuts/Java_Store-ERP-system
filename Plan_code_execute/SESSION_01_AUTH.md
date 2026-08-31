# SESSION 01 - Auth Lokal, RBAC & TenantFilter

## Tujuan
Auth offline tanpa cloud: login BCrypt, JWT lokal 8 jam, RBAC ADMIN/MANAGER/CASHIER/WAREHOUSE, Hibernate TenantFilter.

## Scope
- `core/domain/User`, `Role` enum, `UserRepository`
- `security/AuthService` (BCrypt verify, JWT HS256 via jjwt, secret di OS keychain / `~/.sosha/secret.key`)
- `security/TenantFilter` Hibernate `@FilterDef` + `TenantContext`
- `security/RbacInterceptor` untuk JavaFX route guard

## Task Detail
1. Entity `users(id, tenant_id, branch_id, username UNIQUE, password_hash, role, active, sync_policy=PRIVATE)`
2. `AuthService.login(username, password)` -> verify BCrypt -> generate JWT -> store TenantContext
3. `JwtService` create/validate, claims `userId, tenantId, branchId, role`
4. Hibernate Filter enable per session `em.enableFilter("tenantFilter").setParameter("tenantId", ctx.tenantId)`
5. Seed admin `admin/sosha123` via Flyway V2
6. JavaFX `LoginController` FXML

## Deliverable
- Login berhasil offline, JWT valid 8 jam
- Query `products` auto filtered by tenant

## File
- `core/domain/User.java`, `security/AuthService.java`, `security/JwtService.java`, `security/TenantContext.java`, `ui/LoginController.java`

## Kriteria Banding (untuk Report)
- Waktu login <200ms (SQLite & PG)
- TenantFilter test pass
- BCrypt strength 10

## Estimasi: 3 hari
