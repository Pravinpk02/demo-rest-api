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
