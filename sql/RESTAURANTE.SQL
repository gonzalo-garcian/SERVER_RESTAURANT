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
INSERT INTO USERS VALUES( "1", "Gonzalo", "García Navarro", "", "1" , 4);

#DISH
#STARTERS
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Edamame",200,"Soja verda","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.80","Yaki edamame",200,"Soja verda a la plantxa","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Kimchi",200,"Verdures fermentades picants","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Hiyayakko",200,"Tofu amb salsa de soja","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("2.90","Olives fregides",200," ","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("6.50","Amanida de tonyina i alvocat",200," ","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("6.50","Amanida de Butashabu",200,"amb salsa de sésam blanc","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("6.30","Amanida d'algues amb salsa de cirera",200," ","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Amanida de Tofu",200," ","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("4.90","Tofu amb alvocat salsa de soja i Wasabi",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("5.30","Tofu amb butashabu i Kimchi",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("6.50","Torikara",200,"Karaague de pollastre","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("6.90","Toripon",200,"Karaague de pollastre amb salsa ponzu i nap daikon","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("5.50","Zurikara",200,"Karaague de pedres de pollastre","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("8.30","Ánemones fregides",200," ","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("6.30","Croqueta d'alvocat",200,"Soja verda","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("8.30","Tartar de tonyina i alvocat",200,"","43721530G");
#SPECIAL
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("14.90","Tataki de tonyina arrebossat amb Panko",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("10.90","Teriyaki de pollastre",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("13.50","Shogayaki de secret ibèric amb salsa de gingebre",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("21.50","Entrecot tipus Kobe 250gr",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("13.90","Tonkatsu de llom",200,"","43721530G");
#DRINK
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("21.00","Karatanba Honjozo (Hyogo)",200,"Sake - ampolla");
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("30.00","Fukuju Ginjo (Hyogo)",200," ");
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("33.00","Choubee Daiginjo (Hyogo)",200,"Sake");
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("4.50","Ichiko Cebada",200,"Shochu");
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("4.80","Kaido Boniato",200,"Shochu");
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("3.40","Choya Umeshu",200,"Umeshu");
INSERT INTO DRINK (price, name_drink, quantity_stock, description_drink) VALUES ("7.00","Whisky Hibiki 12 anys",200,"Xupito");
#DESERTS
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Dorayaki",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Flam de sèsam negre",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Flam de te verd",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Cheese cake amb ginebre",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Coulant de xocolata",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Gelatina de Sake amb llima",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Trufas Japoneses de Wasabi i Sake",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Sorbet Llimona de Gandía",200,"","43721530G");
INSERT INTO DISH (price, name_dish, quantity_stock, description_dish, dni_kitchen) VALUES ("3.50","Gelat de Xocolata Belga",200,"","43721530G");


insert into dinner_table(location_table, number_diners_table) VALUES ("Interior", 4);
insert into dinner_table(location_table, number_diners_table) VALUES ("Interior", 4);
insert into dinner_table(location_table, number_diners_table) VALUES ("Interior", 2);
insert into dinner_table(location_table, number_diners_table) VALUES ("Interior", 2);
insert into dinner_table(location_table, number_diners_table) VALUES ("Terraza", 4);
insert into dinner_table(location_table, number_diners_table) VALUES ("Terraza", 4);
insert into dinner_table(location_table, number_diners_table) VALUES ("Terraza", 2);
insert into dinner_table(location_table, number_diners_table) VALUES ("Terraza", 2);

insert into ticket(total_price, id_table) VALUES (50,1); 
insert into ticket(total_price, id_table) VALUES (80,2); 
insert into ticket(total_price, id_table) VALUES (120,1); 
insert into ticket(total_price, id_table) VALUES (30,5); 

insert into hasdish values(4,3,2);
insert into hasdish value(2,4,4);
insert into hasdrink values(2,4,5);

insert into hasdish values(1,1,1);
insert into hasdish value(1,2,1);
