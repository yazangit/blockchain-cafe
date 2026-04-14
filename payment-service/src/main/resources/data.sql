INSERT IGNORE INTO payments (id, order_id, method, amount, status, crypto_ref, created_at, confirmed_at) VALUES
('pay-seed-001', 'order-seed-001', 'CRYPTO_USDC_SIM', 8.50, 'CONFIRMED', 'usdc-demo-seed-001', NOW(), NOW());
