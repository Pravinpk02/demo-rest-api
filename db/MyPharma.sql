CREATE DATABASE bischen_db;
USE bischen_db;
CREATE TABLE usersdb (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  email      VARCHAR(100) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO usersdb (email, password)
VALUES ('sensaipravin@gmail.com', 'Pravin0@2');
SELECT * FROM usersdb;

CREATE TABLE IF NOT EXISTS usersdbdetails (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    google_id   VARCHAR(255) UNIQUE,
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(15),
    password     VARCHAR(255) NOT NULL,
    is_agreed    TINYINT(1)   DEFAULT 0,
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
ALTER TABLE usersdbdetails 
MODIFY COLUMN password VARCHAR(255) NULL;

ALTER TABLE usersdbdetails
ADD COLUMN reset_token VARCHAR(255) NULL,
ADD COLUMN reset_token_expiry DATETIME NULL;
SELECT * FROM usersdbdetails;

-- Orders Table definition
CREATE TABLE IF NOT EXISTS orders (
    id           BIGINT NOT NULL AUTO_INCREMENT,
    order_number VARCHAR(255) NULL,
    customer     VARCHAR(255) NULL,
    city         VARCHAR(255) NULL,
    category     VARCHAR(255) NULL,
    amount       VARCHAR(255) NULL,
    status       VARCHAR(255) NULL,
    items        INT NULL,
    date         VARCHAR(255) NULL,
    address      VARCHAR(255) NULL,
    phone        VARCHAR(255) NULL,
    gstin        VARCHAR(255) NULL,
    products     TEXT NULL,
    timeline     TEXT NULL,
    PRIMARY KEY (id)
);

INSERT INTO orders (order_number, customer, city, category, amount, status, items, date, address, phone, gstin, products, timeline) VALUES 
('ORD-2841', 'City Hospital', 'Chennai', 'Antibiotics, Vitamins', '₹1,24,000', 'delivered', 24, '10 May 2026', '14, Anna Salai, Chennai – 600002', '+91 44 2345 6789', '33AABCC1234D1Z5', '[{"name":"Amoxicillin 500mg","qty":"200 strips","amt":"₹42,000"},{"name":"Vitamin C 1000mg","qty":"150 boxes","amt":"₹36,000"},{"name":"Azithromycin 250mg","qty":"100 strips","amt":"₹46,000"}]', '[{"time":"10 May, 09:14 AM","event":"Order Placed","sub":"By Dr. Ramesh Kumar","done":true},{"time":"10 May, 11:30 AM","event":"Order Confirmed","sub":"Verified by warehouse","done":true},{"time":"10 May, 03:00 PM","event":"Dispatched","sub":"Via BlueDart · TRK8423842","done":true},{"time":"11 May, 10:20 AM","event":"Delivered","sub":"Received by Dr. Ramesh","done":true}]'),
('ORD-2840', 'Apollo Pharmacy', 'Bengaluru', 'Cardiology', '₹89,500', 'processing', 16, '10 May 2026', '27, MG Road, Bengaluru – 560001', '+91 80 4567 8901', '29AABCA5678E2Z3', '[{"name":"Atorvastatin 40mg","qty":"300 strips","amt":"₹54,000"},{"name":"Metoprolol 50mg","qty":"200 strips","amt":"₹35,500"}]', '[{"time":"10 May, 08:30 AM","event":"Order Placed","sub":"Online portal","done":true},{"time":"10 May, 10:00 AM","event":"Payment Confirmed","sub":"NEFT ₹89,500","done":true},{"time":"10 May, 02:00 PM","event":"Processing","sub":"Picking & packing","done":true},{"time":"Pending","event":"Dispatch","sub":"Estimated 11 May","done":false}]'),
('ORD-2839', 'MedPlus', 'Hyderabad', 'Diabetes, Supplements', '₹2,18,750', 'shipped', 38, '09 May 2026', '88, Banjara Hills, Hyderabad – 500034', '+91 40 6789 0123', '36AABCM3456F3Z1', '[{"name":"Metformin 1000mg","qty":"500 strips","amt":"₹95,000"},{"name":"Glipizide 5mg","qty":"400 strips","amt":"₹68,000"},{"name":"Vitamin D3 1000IU","qty":"300 boxes","amt":"₹55,750"}]', '[{"time":"09 May, 07:00 AM","event":"Order Placed","sub":"Bulk order","done":true},{"time":"09 May, 09:00 AM","event":"Confirmed & Invoiced","sub":"Invoice #INV-3341","done":true},{"time":"09 May, 04:00 PM","event":"Dispatched","sub":"DHL · DHLIND8823","done":true},{"time":"Pending","event":"Delivery","sub":"Expected 12 May","done":false}]'),
('ORD-2838', 'Fortis Pharmacy', 'Mumbai', 'Antibiotics', '₹45,200', 'delivered', 12, '09 May 2026', 'Fortis Hospital, Mulund, Mumbai – 400080', '+91 22 2345 0987', '27AABCF7890G4Z2', '[{"name":"Cephalexin 500mg","qty":"120 strips","amt":"₹28,800"},{"name":"Doxycycline 100mg","qty":"80 strips","amt":"₹16,400"}]', '[{"time":"09 May, 06:30 AM","event":"Order Placed","done":true},{"time":"09 May, 08:00 AM","event":"Confirmed","done":true},{"time":"09 May, 01:00 PM","event":"Dispatched","sub":"DTDC · DTDC7712","done":true},{"time":"10 May, 09:00 AM","event":"Delivered","sub":"Received by pharmacy","done":true}]'),
('ORD-2837', 'LifeCare Stores', 'Pune', 'Vitamins, Analgesics', '₹67,300', 'cancelled', 9, '08 May 2026', 'FC Road, Shivajinagar, Pune – 411005', '+91 20 3456 7890', '27AABCL2345H5Z8', '[{"name":"Paracetamol 650mg","qty":"200 strips","amt":"₹24,000"},{"name":"Vitamin B12","qty":"150 boxes","amt":"₹43,300"}]', '[{"time":"08 May, 10:00 AM","event":"Order Placed","done":true},{"time":"08 May, 11:30 AM","event":"Confirmed","done":true},{"time":"08 May, 03:00 PM","event":"Cancelled by customer","sub":"Reason: Duplicate order","done":true}]'),
('ORD-2836', 'Medanta', 'New Delhi', 'Cardiology, Diabetes', '₹3,42,000', 'delivered', 52, '08 May 2026', 'Sector 38, Gurgaon, Haryana – 122001', '+91 124 4141414', '06AABCM1234I6Z4', '[{"name":"Amlodipine 10mg","qty":"600 strips","amt":"₹1,20,000"},{"name":"Metformin 500mg","qty":"800 strips","amt":"₹1,12,000"},{"name":"Rosuvastatin 20mg","qty":"400 strips","amt":"₹1,10,000"}]', '[{"time":"08 May, 06:00 AM","event":"Bulk Order Placed","done":true},{"time":"08 May, 08:30 AM","event":"Verified & Approved","done":true},{"time":"08 May, 02:00 PM","event":"Dispatched","sub":"Own logistics","done":true},{"time":"09 May, 11:00 AM","event":"Delivered","done":true}]'),
('ORD-2835', 'Max Healthcare', 'Noida', 'Antibiotics', '₹98,400', 'delivered', 20, '07 May 2026', 'Max Super Speciality, Sector 19, Noida – 201301', '+91 120 4567890', '09AABCM9012J7Z3', '[{"name":"Meropenem 1g","qty":"50 vials","amt":"₹60,000"},{"name":"Vancomycin 500mg","qty":"80 vials","amt":"₹38,400"}]', '[{"time":"07 May, 09:00 AM","event":"Order Placed","done":true},{"time":"07 May, 10:30 AM","event":"Confirmed","done":true},{"time":"07 May, 04:30 PM","event":"Dispatched","done":true},{"time":"08 May, 10:00 AM","event":"Delivered","done":true}]'),
('ORD-2834', 'Cloudnine Hospital', 'Bengaluru', 'Supplements, Vitamins', '₹56,700', 'delivered', 30, '07 May 2026', 'Cloudnine Hospital, Jayanagar, Bengaluru – 560041', '+91 80 6789 1234', '29AABCC6789K8Z7', '[{"name":"Folic Acid 5mg","qty":"300 strips","amt":"₹18,000"},{"name":"Iron + Folic","qty":"250 strips","amt":"₹22,500"},{"name":"Calcium + Vit D3","qty":"200 boxes","amt":"₹16,200"}]', '[{"time":"07 May, 07:30 AM","event":"Order Placed","done":true},{"time":"07 May, 09:00 AM","event":"Confirmed","done":true},{"time":"07 May, 02:00 PM","event":"Dispatched","done":true},{"time":"08 May, 09:30 AM","event":"Delivered","done":true}]'),
('ORD-2833', 'Narayana Health', 'Kolkata', 'Cardiology', '₹1,34,500', 'delivered', 18, '06 May 2026', 'Narayana Multispeciality, Howrah, Kolkata – 711102', '+91 33 7122 7122', '19AABCN4567L9Z2', '[{"name":"Digoxin 0.25mg","qty":"400 strips","amt":"₹64,000"},{"name":"Furosemide 40mg","qty":"300 strips","amt":"₹45,000"},{"name":"Spironolactone 50mg","qty":"200 strips","amt":"₹25,500"}]', '[{"time":"06 May, 08:00 AM","event":"Order Placed","done":true},{"time":"06 May, 10:00 AM","event":"Confirmed","done":true},{"time":"06 May, 03:00 PM","event":"Dispatched","done":true},{"time":"07 May, 02:00 PM","event":"Delivered","done":true}]'),
('ORD-2832', 'Ruby Hall Clinic', 'Pune', 'Analgesics', '₹34,800', 'processing', 14, '06 May 2026', '40, Sassoon Road, Pune – 411001', '+91 20 6645 6645', '27AABCR3456M0Z6', '[{"name":"Tramadol 50mg","qty":"200 strips","amt":"₹18,000"},{"name":"Diclofenac 75mg","qty":"180 strips","amt":"₹16,800"}]', '[{"time":"06 May, 11:00 AM","event":"Order Placed","done":true},{"time":"06 May, 12:30 PM","event":"Payment Confirmed","done":true},{"time":"06 May, 04:00 PM","event":"Processing","sub":"Warehouse picking","done":true},{"time":"Pending","event":"Dispatch","sub":"Expected 07 May","done":false}]'),
('ORD-2831', 'KIMS Hospital', 'Hyderabad', 'Antibiotics, Cardiology', '₹2,76,000', 'delivered', 44, '05 May 2026', 'Minister Road, Secunderabad – 500003', '+91 40 4488 5000', '36AABCK5678N1Z9', '[{"name":"Piperacillin+Tazo","qty":"100 vials","amt":"₹1,20,000"},{"name":"Clopidogrel 75mg","qty":"500 strips","amt":"₹87,500"},{"name":"Aspirin 75mg","qty":"600 strips","amt":"₹68,500"}]', '[{"time":"05 May, 07:00 AM","event":"Order Placed","done":true},{"time":"05 May, 09:00 AM","event":"Confirmed","done":true},{"time":"05 May, 01:00 PM","event":"Dispatched","done":true},{"time":"06 May, 10:00 AM","event":"Delivered","done":true}]'),
('ORD-2830', 'SRL Diagnostics', 'Mumbai', 'Supplements', '₹22,400', 'pending', 8, '05 May 2026', 'SRL House, Goregaon, Mumbai – 400062', '+91 22 6752 6752', '27AABCS7890O2Z5', '[{"name":"Zinc 50mg","qty":"100 strips","amt":"₹12,000"},{"name":"Omega 3 1000mg","qty":"80 boxes","amt":"₹10,400"}]', '[{"time":"05 May, 02:00 PM","event":"Order Placed","done":true},{"time":"Pending","event":"Payment Confirmation","sub":"Awaiting NEFT","done":false}]'),
('ORD-2829', 'Dr. Agarwal Eye Hospital', 'New Delhi', 'Supplements', '₹45,600', 'delivered', 16, '04 May 2026', 'Dr. Agarwal Eye Care, Rajendra Place, New Delhi – 110008', '+91 11 4567 0000', '07AABCA8901P3Z1', '[{"name":"Lutein + Zeaxanthin","qty":"200 boxes","amt":"₹28,000"},{"name":"Vitamin A 5000IU","qty":"150 boxes","amt":"₹17,600"}]', '[{"time":"04 May, 10:00 AM","event":"Order Placed","done":true},{"time":"04 May, 11:30 AM","event":"Confirmed","done":true},{"time":"04 May, 03:00 PM","event":"Dispatched","done":true},{"time":"05 May, 10:00 AM","event":"Delivered","done":true}]'),
('ORD-2828', 'Apollo Clinic', 'Pune', 'Cardiology, Diabetes', '₹1,78,900', 'shipped', 32, '04 May 2026', 'Apollo Clinic, Bund Garden, Pune – 411001', '+91 20 6600 5000', '27AABCA2345Q7Z4', '[{"name":"Atenolol 50mg","qty":"400 strips","amt":"₹52,000"},{"name":"Lisinopril 10mg","qty":"300 strips","amt":"₹66,000"},{"name":"Glibenclamide 5mg","qty":"250 strips","amt":"₹60,900"}]', '[{"time":"04 May, 08:00 AM","event":"Order Placed","done":true},{"time":"04 May, 09:30 AM","event":"Confirmed","done":true},{"time":"04 May, 02:00 PM","event":"Dispatched","sub":"Fedex · FDX45823","done":true},{"time":"In Transit","event":"Delivery","sub":"Expected 06 May","done":false}]'),
('ORD-2827', 'Breach Candy Hospital', 'Mumbai', 'Antibiotics', '₹87,500', 'delivered', 22, '03 May 2026', '60-A, Bhulabhai Desai Road, Mumbai – 400026', '+91 22 2368 3888', '27AABCB6789R5Z8', '[{"name":"Levofloxacin 500mg","qty":"300 strips","amt":"₹45,000"},{"name":"Cefixime 200mg","qty":"250 strips","amt":"₹42,500"}]', '[{"time":"03 May, 09:00 AM","event":"Order Placed","done":true},{"time":"03 May, 10:30 AM","event":"Confirmed","done":true},{"time":"03 May, 03:30 PM","event":"Dispatched","done":true},{"time":"04 May, 11:00 AM","event":"Delivered","done":true}]'),
('ORD-2826', 'Manipal Hospital', 'Bengaluru', 'Analgesics, Supplements', '₹63,200', 'cancelled', 24, '03 May 2026', 'Manipal Hospital, CV Raman Nagar, Bengaluru – 560093', '+91 80 4044 0000', '29AABCM0123S6Z2', '[{"name":"Ibuprofen 400mg","qty":"400 strips","amt":"₹32,000"},{"name":"Acetaminophen 500mg","qty":"200 strips","amt":"₹16,000"},{"name":"Magnesium","qty":"150 boxes","amt":"₹15,200"}]', '[{"time":"03 May, 11:00 AM","event":"Order Placed","done":true},{"time":"03 May, 01:00 PM","event":"Confirmed","done":true},{"time":"03 May, 04:00 PM","event":"Cancelled by customer","sub":"Reason: Price negotiation","done":true}]'),
('ORD-2825', 'St. Johns Medical', 'Bengaluru', 'Cardiology', '₹92,400', 'delivered', 20, '02 May 2026', 'St. Johns Medical College Hospital, Bengaluru – 560034', '+91 80 4343 3333', '29AABCS9012T1Z7', '[{"name":"Ramipril 5mg","qty":"300 strips","amt":"₹54,000"},{"name":"Nitroglycerin 0.6mg","qty":"150 strips","amt":"₹38,400"}]', '[{"time":"02 May, 08:30 AM","event":"Order Placed","done":true},{"time":"02 May, 10:00 AM","event":"Confirmed","done":true},{"time":"02 May, 02:00 PM","event":"Dispatched","done":true},{"time":"03 May, 09:30 AM","event":"Delivered","done":true}]'),
('ORD-2824', 'Vimta Labs', 'Hyderabad', 'Antibiotics', '₹1,12,600', 'delivered', 28, '02 May 2026', 'Vimta Labs, Madhapur, Hyderabad – 500081', '+91 40 2358 6789', '36AABCV2345U8Z4', '[{"name":"Ciprofloxacin 500mg","qty":"400 strips","amt":"₹56,000"},{"name":"Gentamicin 80mg","qty":"200 vials","amt":"₹56,600"}]', '[{"time":"02 May, 07:00 AM","event":"Order Placed","done":true},{"time":"02 May, 08:30 AM","event":"Confirmed","done":true},{"time":"02 May, 01:00 PM","event":"Dispatched","done":true},{"time":"03 May, 10:00 AM","event":"Delivered","done":true}]'),
('ORD-2823', 'Fortis Escorts Hospital', 'New Delhi', 'Cardiology, Antibiotics', '₹2,34,500', 'processing', 36, '01 May 2026', 'Fortis Escorts Hospital, Okhla Road, New Delhi – 110025', '+91 11 4200 4200', '07AABCF3456V9Z6', '[{"name":"Verapamil 120mg","qty":"300 strips","amt":"₹1,05,000"},{"name":"Imipenem + Cilastatin","qty":"80 vials","amt":"₹96,000"},{"name":"Doxycycline 100mg","qty":"300 strips","amt":"₹33,500"}]', '[{"time":"01 May, 06:00 AM","event":"Order Placed","done":true},{"time":"01 May, 08:00 AM","event":"Verified & Approved","done":true},{"time":"01 May, 02:00 PM","event":"Processing","sub":"Warehouse picking in progress","done":true},{"time":"Pending","event":"Dispatch","sub":"Expected 02 May","done":false}]'),
('ORD-2822', 'Lilavati Hospital', 'Mumbai', 'Vitamins', '₹78,400', 'delivered', 32, '01 May 2026', '15, New Marine Lines, Mumbai – 400020', '+91 22 6767 7777', '27AABCL4567W2Z9', '[{"name":"Vitamin C 500mg","qty":"500 boxes","amt":"₹45,000"},{"name":"Vitamin E 400IU","qty":"300 boxes","amt":"₹33,400"}]', '[{"time":"01 May, 10:00 AM","event":"Order Placed","done":true},{"time":"01 May, 11:30 AM","event":"Confirmed","done":true},{"time":"01 May, 03:00 PM","event":"Dispatched","done":true},{"time":"02 May, 10:00 AM","event":"Delivered","done":true}]'),
('ORD-2821', 'Reliance Health', 'Jaipur', 'Diabetes', '₹1,45,800', 'delivered', 40, '30 Apr 2026', 'Reliance Hospital, Jaipur – 302001', '+91 141 5166 5166', '08AABCR5678X3Z5', '[{"name":"Insulin Glargine","qty":"30 pens","amt":"₹75,000"},{"name":"Pioglitazone 15mg","qty":"400 strips","amt":"₹42,800"},{"name":"Sitagliptin 50mg","qty":"300 strips","amt":"₹28,000"}]', '[{"time":"30 Apr, 08:00 AM","event":"Order Placed","done":true},{"time":"30 Apr, 09:30 AM","event":"Confirmed","done":true},{"time":"30 Apr, 01:00 PM","event":"Dispatched","done":true},{"time":"01 May, 04:00 PM","event":"Delivered","done":true}]');

SELECT * FROM orders;


