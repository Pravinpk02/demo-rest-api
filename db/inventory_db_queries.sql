-- ==========================================
-- MYPHARMA INVENTORY DATABASE SCHEMA & QUERIES
-- ==========================================

-- ------------------------------------------
-- 1. DATABASE TABLES INITIALIZATION (DDL)
-- ------------------------------------------

-- Table: Suppliers
CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    location VARCHAR(200) NOT NULL,
    avatar VARCHAR(10),
    color VARCHAR(20),
    products_count INT DEFAULT 0,
    rating DECIMAL(2,1) DEFAULT 0.0,
    delivery_days INT NOT NULL,
    phone VARCHAR(20)
);

-- Table: Products
CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(50) PRIMARY KEY, -- e.g. 'PRD-001'
    name VARCHAR(200) NOT NULL,
    category VARCHAR(100) NOT NULL,
    batch VARCHAR(50) NOT NULL,
    sku VARCHAR(100) UNIQUE NOT NULL,
    stock_percent INT NOT NULL DEFAULT 100, -- 0 to 100 representing fill level
    units INT NOT NULL DEFAULT 0, -- Count of units in stock
    unit_price DECIMAL(10,2) NOT NULL,
    expiry_date DATE NOT NULL,
    supplier_name VARCHAR(100) NOT NULL,
    status VARCHAR(50) GENERATED ALWAYS AS (
        CASE 
            WHEN units <= 0 THEN 'OOS'
            WHEN stock_percent <= 20 THEN 'Critical'
            WHEN stock_percent <= 40 THEN 'Low'
            ELSE 'Good'
        END
    ) STORED,
    FOREIGN KEY (supplier_name) REFERENCES suppliers(name) ON UPDATE CASCADE
);

-- Table: Product History Log
CREATE TABLE IF NOT EXISTS product_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(50) NOT NULL,
    transaction_date DATE NOT NULL,
    quantity VARCHAR(50) NOT NULL,
    cost DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Table: Purchase Orders (Reorders)
CREATE TABLE IF NOT EXISTS purchase_orders (
    id VARCHAR(100) PRIMARY KEY,
    product_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    supplier_name VARCHAR(100) NOT NULL,
    delivery_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_name) REFERENCES suppliers(name) ON UPDATE CASCADE
);


-- ------------------------------------------
-- 2. INITIAL SEED MOCK DATA (DML)
-- ------------------------------------------

-- Populate Suppliers
INSERT INTO suppliers (name, location, avatar, color, products_count, rating, delivery_days, phone) VALUES
('Sun Phaarma', 'Mumbai, Maharashtra', 'SP', '#0F6E56', 842, 4.9, 2, '+91 22 4324 4324'),
('Ciipla Ltd', 'Mumbai, Maharashtra', 'CL', '#378ADD', 624, 4.8, 3, '+91 22 2482 6000'),
('Abbottt India', 'Mumbai, Maharashtra', 'AI', '#EF9F27', 412, 4.9, 1, '+91 22 6778 6778'),
('Dr. Reddyy\'s', 'Hyderabad, Telangana', 'DR', '#D4537E', 538, 4.7, 2, '+91 40 4900 2900');

-- Populate Products
INSERT INTO products (id, name, category, batch, sku, stock_percent, units, unit_price, expiry_date, supplier_name) VALUES
('PRD-011', 'Amoxicillin 500mg', 'Antibiotics', 'AMX-A204', 'SKU-1011', 12, 480, 210.00, '2026-08-01', 'Sun Pharma'),
('PRD-012', 'Paracetamol 650mg', 'Analgesics', 'PAR-P302', 'SKU-1092', 8, 320, 12.00, '2026-09-01', 'Cipla Ltd'),
('PRD-013', 'Metformin 1000mg', 'Diabetes', 'MET-D119', 'SKU-1013', 32, 1280, 190.00, '2026-12-01', 'Dr. Reddy\'s'),
('PRD-014', 'Atorvasuutatin Ptk 40mg', 'Cardiology', 'ATO-C511', 'SKU-1014', 27, 1080, 280.00, '2026-07-01', 'Sun Pharma'),
('PRD-015', 'Vitamin D3 1000IU', 'Vitamins', 'VIT-V088', 'SKU-1015', 65, 2600, 185.00, '2027-03-01', 'Abbott India'),
('PRD-016', 'Azithromycin 250mg', 'Antibiotics', 'AZI-A312', 'SKU-1016', 74, 2960, 460.00, '2027-02-01', 'Cipla Ltd');

-- Populate Product History
INSERT INTO product_history (product_id, transaction_date, quantity, cost) VALUES
('PRD-001', '2026-03-01', '2,000 units', 420000.00),
('PRD-004', '2026-01-01', '3,000 units', 630000.00),
('PRD-002', '2026-04-01', '4,000 units', 480000.00),
('PRD-003', '2026-04-01', '2,000 units', 380000.00);

DELETE FROM products;
