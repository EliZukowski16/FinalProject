<<<<<<< HEAD

 
CREATE DATABASE liquor_store;

=======
DROP DATABASE liquor_store;
 
CREATE DATABASE liquor_store;

USE liquor_store;

>>>>>>> b088a857180722688a04a59972446cc18e050127
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
<<<<<<< HEAD
UNIQUE (username),
=======
UNIQUE (username))
>>>>>>> b088a857180722688a04a59972446cc18e050127
ENGINE = InnoDB;


CREATE TABLE core_product
(id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
type ENUM('BEER', 'WINE', 'SPIRITS'),
subtype VARCHAR(50) NOT NULL,
<<<<<<< HEAD
description TEXT(1000) NOT NULL,
=======
description TEXT(1000) NOT NULL)
>>>>>>> b088a857180722688a04a59972446cc18e050127
ENGINE = InnoDB;


CREATE TABLE product_tags
(core_product_id INT(10) NOT NULL,
name VARCHAR(50) NOT NULL,
<<<<<<< HEAD
PRIMARY KEY (product_id, tags),
CONSTRAINT productTags_fk_1 FOREIGN KEY (core_product_id) REFERENCES core_product(id) ON DELETE CASCADE,
=======
PRIMARY KEY (core_product_id, name),
CONSTRAINT productTags_fk_1 FOREIGN KEY (core_product_id) REFERENCES core_product(id) ON DELETE CASCADE)
ENGINE = InnoDB;
>>>>>>> b088a857180722688a04a59972446cc18e050127


CREATE TABLE product
(id INT AUTO_INCREMENT PRIMARY KEY,
core_product_id int(10) NOT NULL,
base_unit VARCHAR(50) NOT NULL,
quantity INT(10) NOT NULL,
inventory INT(10) NOT NULL,
price decimal(12,2) NOT NULL,
CONSTRAINT product_fk_1 FOREIGN KEY (core_product_id) REFERENCES core_product(id) ON DELETE CASCADE)
ENGINE = InnoDB;


<<<<<<< HEAD
CREATE TABLE order
=======
CREATE TABLE _order
>>>>>>> b088a857180722688a04a59972446cc18e050127
(id INT AUTO_INCREMENT PRIMARY KEY,
customer_id INT(10) NOT NULL,
date TIMESTAMP NOT NULL,
total DECIMAL(12,2) NOT NULL,
<<<<<<< HEAD
CONSTRAINT order_fk_1FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE)
=======
CONSTRAINT order_fk_1 FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE)
>>>>>>> b088a857180722688a04a59972446cc18e050127
ENGINE = InnoDB;


CREATE TABLE order_detail
(order_id int(10) NOT NULL,
product_id int(10) NOT NULL,
quantity int(10) NOT NULL,
unit_price DECIMAL(12,2) NOT NULL,
PRIMARY KEY (order_id, product_id),
<<<<<<< HEAD
CONSTRAINT order_detail_fk_1 FOREIGN KEY (order_id) REFERENCES order(id) ON DELETE CASCADE,
=======
CONSTRAINT order_detail_fk_1 FOREIGN KEY (order_id) REFERENCES _order(id) ON DELETE CASCADE,
>>>>>>> b088a857180722688a04a59972446cc18e050127
CONSTRAINT order_detail_fk_1 FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE sales
(id INT AUTO_INCREMENT PRIMARY KEY,
product_id INT(10) NOT NULL,
year YEAR(4) NOT NULL,
month ENUM('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'),
sales DECIMAL(50) NOT NULL)
ENGINE = InnoDB;


