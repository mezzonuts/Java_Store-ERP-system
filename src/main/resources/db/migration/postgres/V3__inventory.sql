CREATE TABLE IF NOT EXISTS warehouses (
  id UUID PRIMARY KEY,
  branch_id UUID NOT NULL,
  name TEXT NOT NULL,
  active BOOLEAN DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS stock_levels (
  id UUID PRIMARY KEY,
  product_id UUID NOT NULL,
  warehouse_id UUID NOT NULL,
  available_qty NUMERIC(15,3) DEFAULT 0,
  reserved_qty NUMERIC(15,3) DEFAULT 0,
  min_stock_level NUMERIC(15,3),
  CONSTRAINT uk_stock UNIQUE(product_id, warehouse_id)
);
CREATE TABLE IF NOT EXISTS stock_ledger (
  id UUID PRIMARY KEY,
  product_id UUID NOT NULL,
  warehouse_id UUID NOT NULL,
  change_qty NUMERIC(15,3) NOT NULL,
  reference_type TEXT NOT NULL,
  reference_id UUID,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS batches (
  id UUID PRIMARY KEY,
  product_id UUID NOT NULL,
  batch_no TEXT NOT NULL,
  expiry_date DATE,
  qty INTEGER NOT NULL
);
