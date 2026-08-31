CREATE TABLE IF NOT EXISTS users (
  id TEXT PRIMARY KEY,
  tenant_id TEXT NOT NULL,
  branch_id TEXT NOT NULL,
  username TEXT NOT NULL,
  password_hash TEXT NOT NULL,
  role TEXT NOT NULL,
  active INTEGER DEFAULT 1,
  sync_policy TEXT DEFAULT 'PRIVATE',
  UNIQUE (tenant_id, username)
);
CREATE TABLE IF NOT EXISTS sync_outbox (
  id TEXT PRIMARY KEY,
  table_name TEXT NOT NULL,
  row_id TEXT NOT NULL,
  op TEXT NOT NULL,
  payload_json TEXT,
  idempotency_key TEXT UNIQUE NOT NULL,
  created_at TEXT DEFAULT (datetime('now')),
  synced_at TEXT,
  retry INTEGER DEFAULT 0
);
