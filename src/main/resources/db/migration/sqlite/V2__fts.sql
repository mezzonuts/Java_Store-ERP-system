CREATE VIRTUAL TABLE IF NOT EXISTS products_fts USING fts5(name, sku, content='products', content_rowid='rowid');
CREATE TRIGGER IF NOT EXISTS trg_products_ai AFTER INSERT ON products BEGIN INSERT INTO products_fts(rowid,name,sku) VALUES (new.rowid,new.name,new.sku); END;
CREATE TRIGGER IF NOT EXISTS trg_products_ad AFTER DELETE ON products BEGIN DELETE FROM products_fts WHERE rowid=old.rowid; END;
