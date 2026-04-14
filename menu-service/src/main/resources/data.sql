INSERT IGNORE INTO products (id, name, price_gross, vat_rate, active, created_at) VALUES
('p-espresso', 'Espresso', 2.50, 'DE_19', true, NOW()),
('p-latte', 'Latte', 3.80, 'DE_19', true, NOW()),
('p-sandwich', 'Sandwich', 6.00, 'DE_7', true, NOW()),
('p-water', 'Water', 1.50, 'DE_19', true, NOW());
