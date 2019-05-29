/* Base de datos RESTAURANT */

# Creamos la base de datos

DROP DATABASE IF EXISTS RESTAURANT;
CREATE DATABASE RESTAURANT;
DROP USER IF EXISTS RestaurantAdmin;
CREATE USER RestaurantAdmin IDENTIFIED BY 'RestaurantPass';
GRANT ALL PRIVILEGES ON RESTAURANT.* TO RestaurantAdmin WITH GRANT OPTION;
USE RESTAURANT;

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
ALTER TABLE DISH AUTO_INCREMENT=0;

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

#USERS Cliente 1 , Cocina 2, Camarero 3, Admin 4 , va por orden de permisos.
INSERT INTO USERS VALUES( "45992171Z", "Gonzalo", "García Navarro", "", "GonzaloPass" , 4);
INSERT INTO USERS VALUES( "45986926J", "Kevin", "Sánchez González", "", "KevinPass" , 4);
INSERT INTO USERS VALUES( "47740738Y", "Óscar", "González García", "", "ÓscarPass" , 4);
INSERT INTO USERS VALUES( "43721530G", "Juan", "González García", "", "JuanPass" , 2);

#DISH
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("4.50", "Macarrones con tomate", 6, "Pasta con tomate y carne", "43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("4.50", "Mixta", 2, "Ensalada y embutido", "43721530G");