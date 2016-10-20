DROP DATABASE tests_liquor_store;
 
CREATE DATABASE test_liquor_store;

USE test_liquor_store;

CREATE TABLE admin
(id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL,
salt VARCHAR(50) NOT NULL,
hash VARCHAR(50) NOT NULL,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
role INT(6) NOT NULL,
UNIQUE (username))
ENGINE = InnoDB;


CREATE TABLE customer
(id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL,
salt VARCHAR(50) NOT NULL,
hash VARCHAR(50) NOT NULL,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
street VARCHAR(50) NOT NULL,
city VARCHAR(50) NOT NULL,
state VARCHAR(2) NOT NULL,
zip_code VARCHAR(5) NOT NULL,
birth_date TIMESTAMP NOT NULL,
UNIQUE (username))
ENGINE = InnoDB;


CREATE TABLE core_product
(id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
type ENUM('BEER', 'WINE', 'SPIRITS', 'CIDERS','ACCESSORIES','NON_ALCOHOL'),
subtype VARCHAR(50) NOT NULL,
description TEXT(1000) NOT NULL,
full_size_image VARCHAR(200) NOT NULL,
thumbnail VARCHAR(200) NOT NULL)
ENGINE = InnoDB;


CREATE TABLE product_tags
(core_product_id INT(10) NOT NULL,
name VARCHAR(50) NOT NULL,
PRIMARY KEY (core_product_id, name),
CONSTRAINT productTags_fk_1 FOREIGN KEY (core_product_id) REFERENCES core_product(id) ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE product
(id INT AUTO_INCREMENT PRIMARY KEY,
core_product_id int(10) NOT NULL,
base_unit VARCHAR(50) NOT NULL,
quantity INT(10) NOT NULL,
inventory INT(10) NOT NULL,
price decimal(12,2) NOT NULL,
CONSTRAINT product_fk_1 FOREIGN KEY (core_product_id) REFERENCES core_product(id) ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE _order
(id INT AUTO_INCREMENT PRIMARY KEY,
customer_id INT(10) NOT NULL,
date TIMESTAMP NOT NULL,
order_status ENUM('APPROVED', 'PENDING', 'REJECTED') NOT NULL,
total DECIMAL(12,2) NOT NULL,
time_order_placed TIMESTAMP NOT NULL,
CONSTRAINT order_fk_1 FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE order_detail
(order_id int(10) NOT NULL,
product_id int(10) NOT NULL,
quantity int(10) NOT NULL,
unit_price DECIMAL(12,2) NOT NULL,
PRIMARY KEY (order_id, product_id),
CONSTRAINT order_detail_fk_1 FOREIGN KEY (order_id) REFERENCES _order(id) ON DELETE CASCADE,
CONSTRAINT order_detail_fk_2 FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE sales
(id INT AUTO_INCREMENT PRIMARY KEY,
product_id INT(10) NOT NULL,
number INT(10) NOT NULL,
total_value DECIMAL(12,2) NOT NULL,
date_sold DATE NOT NULL,
aggregate_sales BOOLEAN NOT NULL,
CONSTRAINT product_sales_fk_1 FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE)
ENGINE = InnoDB;


