# SESSION 06 - Finance PRIVATE + AuditLog

## Tujuan
Keuangan & HR lokal privat terenkripsi, tidak sync, audit lengkap.

## Scope
- `FinanceLedger`, `Payroll`, `Expense`, `Journal` (all PRIVATE)
- `AuditLog` entity + Hibernate Interceptor
- SQLCipher / pgcrypto + file permission 700

## Task
1. Entities: `finance_ledger(tenant_id, amount, type, profit, PRIVATE)`, `payrolls`, `audit_log(table, row_id, op, old_json, new_json, user_id, ts)`
2. `FinanceService` & `PayrollService` - no outbox call, unit test assert 0 enqueue
3. `AuditInterceptor` via Hibernate `EntityListeners` -> auto insert audit_log on @PreUpdate/@PrePersist
4. Encryption: SQLite `PRAGMA key` via JDBC URL `cipher=aes256`, PG `pgp_sym_encrypt()` for sensitive cols
5. Backup: Quartz 02:00 copy `sosha.db` / `pg_dump`
6. UI `FinanceController` - local reports, no sync indicator

## Deliverable
- Finance insert -> outbox 0, audit_log 1
- `sosha.db` cannot open without key
- Report P/L lokal accurate

## File
- `core/domain/FinanceLedger.java`, `AuditLog.java`, `core/service/FinanceService.java`, `security/AuditInterceptor.java`, `ui/FinanceController.java`

## Kriteria
- Privacy audit pass
- Audit log count == mutations
- Encryption verified

## Estimasi: 3 hari
