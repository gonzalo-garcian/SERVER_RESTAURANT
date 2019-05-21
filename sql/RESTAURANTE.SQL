/* Base de datos RESTAURANT */

# Creamos la base de datos

DROP DATABASE IF EXISTS RESTAURANTE;
CREATE DATABASE RESTAURANTE;
DROP USER IF EXISTS RestauranteAdmin;
CREATE USER RestauranteAdmin IDENTIFIED BY 'RestaurantePass';
GRANT ALL PRIVILEGES ON RESTAURANTE.* TO RestauranteAdmin WITH GRANT OPTION;
USE RESTAURANTE;

# Creamos las tablas

CREATE TABLE USERS(
	dni VARCHAR(9) PRIMARY KEY not null,
    first_name VARCHAR(255) not null,
    surnames VARCHAR(255) not null,
    phone_number VARCHAR(9),
    access_key VARCHAR(255) not null,
    kind int not null default 1
    );
    
CREATE TABLE DINNER_TABLE(
	id_table INT(3) AUTO_INCREMENT PRIMARY KEY not null,
	location_table VARCHAR(255),
	number_diners_table INT(2)
	);
	
CREATE TABLE ATTEND(
	id_table INT(3),
	dni_waiter VARCHAR(9),
    PRIMARY KEY(id_table,dni_waiter)
);

CREATE TABLE TICKET(
	id_ticket INT(3) AUTO_INCREMENT PRIMARY KEY not null,
	total_price FLOAT(10),
	id_table INT(3)
	);

CREATE TABLE DRINK(
	id_item_drink INT(3) AUTO_INCREMENT PRIMARY KEY not null,
	price FLOAT(10) not null,
	name_drink VARCHAR(255) not null,
	quantity_stock INT(10) not null,
	status_drink int not null default 1,
	description_drink VARCHAR(255)
	);
	
CREATE TABLE DISH(
	id_item_dish INT(3) AUTO_INCREMENT PRIMARY KEY not null,
	price FLOAT(10) not null,
	name_dish VARCHAR(255) not null,
	quantity_stock INT(10) not null,
	status_dish int not null default 1,
	description_dish VARCHAR(255),
	dni_kitchen VARCHAR(9)
	);
	
CREATE TABLE HASDISH(
	id_ticket INT(3),
	id_item_dish INT(3),
	quantity_item INT(2),
    PRIMARY KEY(id_ticket,id_item_dish)
);

CREATE TABLE HASDRINK(
	id_ticket INT(3),
	id_item_drink INT(3),
	quantity_item INT(2),
    PRIMARY KEY(id_ticket,id_item_drink)
);

ALTER TABLE DISH
ADD FOREIGN KEY (dni_kitchen) REFERENCES USERS(dni);

ALTER TABLE ATTEND
ADD FOREIGN KEY (dni_waiter) REFERENCES USERS(dni),
ADD FOREIGN KEY (id_table) REFERENCES DINNER_TABLE(id_table);

ALTER TABLE TICKET
ADD FOREIGN KEY (id_table) REFERENCES DINNER_TABLE(id_table);

ALTER TABLE HASDISH
ADD FOREIGN KEY (id_ticket) REFERENCES TICKET(id_ticket),
ADD FOREIGN KEY (id_item_dish) REFERENCES DISH(id_item_dish);

ALTER TABLE HASDRINK
ADD FOREIGN KEY (id_ticket) REFERENCES TICKET(id_ticket),
ADD FOREIGN KEY (id_item_drink) REFERENCES DRINK(id_item_drink);


    