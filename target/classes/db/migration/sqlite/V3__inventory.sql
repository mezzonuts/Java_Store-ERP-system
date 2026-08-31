CREATE TABLE IF NOT EXISTS warehouses (
  id TEXT PRIMARY KEY,
  branch_id TEXT NOT NULL,
  name TEXT NOT NULL,
  active INTEGER DEFAULT 1
);
CREATE TABLE IF NOT EXISTS stock_levels (
  id TEXT PRIMARY KEY,
  product_id TEXT NOT NULL,
  warehouse_id TEXT NOT NULL,
  available_qty NUMERIC(15,3) DEFAULT 0,
  reserved_qty NUMERIC(15,3) DEFAULT 0,
  min_stock_level NUMERIC(15,3),
  UNIQUE(product_id, warehouse_id)
);
CREATE TABLE IF NOT EXISTS stock_ledger (
  id TEXT PRIMARY KEY,
  product_id TEXT NOT NULL,
  warehouse_id TEXT NOT NULL,
  change_qty NUMERIC(15,3) NOT NULL,
  reference_type TEXT NOT NULL,
  reference_id TEXT,
  created_at TEXT DEFAULT (datetime('now'))
);
CREATE TABLE IF NOT EXISTS batches (
  id TEXT PRIMARY KEY,
  product_id TEXT NOT NULL,
  batch_no TEXT NOT NULL,
  expiry_date TEXT,
  qty INTEGER NOT NULL
);
