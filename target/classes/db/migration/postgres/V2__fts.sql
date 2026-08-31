CREATE INDEX IF NOT EXISTS idx_products_name_gin ON products USING GIN (to_tsvector('english', name));
