-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

-- ROLES
insert into roles(rolename) values('ROLE_ADMIN'), ('ROLE_MECHANIC'), ('ROLE_CASHIER');

-- USERS
--   username: admin, password: admin - ROLE_ADMIN
insert into users(username, password) values('admin', '$2a$12$qrE6TwOWnTQJiJa7TNC7/.k127kBw1NEPoGOeykZ8hzmqN3baG4xS');

--   username: Piet, password: password - ROLE_MECHANIC
insert into users(username, password) values('Piet', '$2a$12$Qb.mt09Z7zU20EPy3PtilOCEi.yBAkzBOUzk1yd4b5UL/2tAVFJgm');

--   username: Mirjam, password: password - ROLE_CASHIER
insert into users(username, password) values('Mirjam', '$2a$12$Qb.mt09Z7zU20EPy3PtilOCEi.yBAkzBOUzk1yd4b5UL/2tAVFJgm');

--   username: Kees, password: password - No Role
insert into users(username, password) values('Kees', '$2a$12$Qb.mt09Z7zU20EPy3PtilOCEi.yBAkzBOUzk1yd4b5UL/2tAVFJgm');

--   username: Dirk, password: password - ROLE_ADMIN
insert into users(username, password) values('Dirk', '$2a$12$Qb.mt09Z7zU20EPy3PtilOCEi.yBAkzBOUzk1yd4b5UL/2tAVFJgm');

-- COUPLING TABLE USERS-ROLES
insert into users_roles(username, rolename) values('admin', 'ROLE_ADMIN');
insert into users_roles(username, rolename) values('Piet', 'ROLE_MECHANIC');
insert into users_roles(username, rolename) values('Mirjam', 'ROLE_CASHIER');
insert into users_roles(username, rolename) values('Dirk', 'ROLE_ADMIN');

-- CARPARTS
-- keuring
insert into carparts(name, price) values( 'APK-Keuring', 45.00 );
-- remmen
insert into carparts(name, price) values( 'Remschijf', 95 );
insert into carparts(name, price) values( 'Remslang', 45 );
insert into carparts(name, price) values( 'Remblok', 65 );
-- verlichting
insert into carparts(name, price) values( 'Koplamp', 5.5 );
insert into carparts(name, price) values( 'Achterlicht', 3.5 );
insert into carparts(name, price) values( 'Knipperlicht', 3.5 );
insert into carparts(name, price) values( 'Remlicht', 4.5 );
-- ruitenwisser
insert into carparts(name, price) values( 'Ruitenwisser', 25 );
insert into carparts(name, price) values( 'Ruitenwisser vloeistof', 25 );
-- banden
insert into carparts(name, price) values( 'Autoband', 100 );
-- uitlaat
insert into carparts(name, price) values( 'Katalysator', 250 );
insert into carparts(name, price) values( 'Roetfilter', 85 );
-- verveling
insert into carparts(name, price) values( 'twee mokken koffie', 5 );

-- ACTIONS
insert into actions(description, hr_rate, time, labour, materials, price) values( 'APK Keuring', 45.00, 0.0, 0.00, 45.00, 45.00 );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Repareren remmen', 45.00, 2.5, 112.50, 205, 317.50 );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Vervangen verlichting', 45.00, 1.5, 67.50, 17, 84.50  );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Vervangen ruitenwissers', 45.00, 0.25, 11.25, 50, 64.25  );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Bandenmontage', 45.00, 0.5, 22.50, 100, 122.50 );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Katalisator vervangen', 45.00, 2.5, 112.50,0.0, 0 );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Roetfilter vervangen', 45.00, 1.5, 45.0, 0, 0 );
insert into actions(description, hr_rate, time, labour, materials, price) values( 'Verveling', 45.00, 1.0, 45.0, 0, 0 );

-- COUPLING TABLE
insert into actions_car_parts(actions_id, car_parts_id) values( 1, 1 );
insert into actions_car_parts(actions_id, car_parts_id) values( 2, 2 );
insert into actions_car_parts(actions_id, car_parts_id) values( 2, 3 );
insert into actions_car_parts(actions_id, car_parts_id) values( 2, 4 );
insert into actions_car_parts(actions_id, car_parts_id) values( 3, 5 );
insert into actions_car_parts(actions_id, car_parts_id) values( 3, 6 );
insert into actions_car_parts(actions_id, car_parts_id) values( 3, 7 );
insert into actions_car_parts(actions_id, car_parts_id) values( 3, 8 );
insert into actions_car_parts(actions_id, car_parts_id) values( 4, 9 );
insert into actions_car_parts(actions_id, car_parts_id) values( 4, 10 );
insert into actions_car_parts(actions_id, car_parts_id) values( 5, 11 );

-- CUSTOMERS
insert into customers( first_name, last_name, dob) values( 'Wim', 'Theijken', '1962-02-01');
insert into customers( first_name, last_name, dob) values( 'Klaas', 'Vaak', '1951-01-01');
insert into customers( first_name, last_name, dob) values( 'Karel', 'Appel', '1981-01-10');
insert into customers( first_name, last_name, dob) values( 'Marie', 'Koekoek', '1981-01-10');
insert into customers( first_name, last_name, dob) values( 'Afval', 'Bak', '1981-01-10');

-- CARS
insert into cars(license_plate, brand, type, color, age, customer_id ) values('96-ZN-ZX', 'Toyota', 'Aygo', 'Red', '2008-04-29', 1 );
insert into cars(license_plate, brand, type, color, age, customer_id ) values('SL-AP-EN', 'Slaap', 'Diep', 'Donker', '2000-01-01', 2 );
insert into cars(license_plate, brand, type, color, age, customer_id ) values('AX-BY-69', 'Afval', 'Bak', 'Black', '2024-01-01', 4 );

-- TICKETS
insert into tickets(date, price, car_license_plate ) values( '2025-01-23', 633.75, '96-ZN-ZX' );
insert into tickets(date, price, car_license_plate ) values( '2025-01-23', 0.0, 'SL-AP-EN' );
insert into tickets(date, price, car_license_plate ) values( '2025-01-23', 0.0, 'SL-AP-EN' );

-- COUPLING TABLE
insert into tickets_actions(actions_id, tickets_id) values( 1, 1 );
insert into tickets_actions(actions_id, tickets_id) values( 2, 1 );
insert into tickets_actions(actions_id, tickets_id) values( 3, 1 );
insert into tickets_actions(actions_id, tickets_id) values( 4, 1 );
insert into tickets_actions(actions_id, tickets_id) values( 5, 1 );

--INVOICES
insert into invoices( date, vat_percentage, price, vat, total, ticket_id, customer_id ) values( '2024-08-09', 21.00, 633.75, 133.08, 766.83, 1 ,1 );