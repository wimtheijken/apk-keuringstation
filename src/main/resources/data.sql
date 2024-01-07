-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

insert into roles(rolename) values  ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MECHANIC'), ('ROLE_CASHIER');

--   username: admin, password: admin
insert into users(username, password) values ('admin', '$2a$12$qrE6TwOWnTQJiJa7TNC7/.k127kBw1NEPoGOeykZ8hzmqN3baG4xS');

--   username: Piet, password: password
insert into users(username, password) values ('Piet', '$2a$12$Qb.mt09Z7zU20EPy3PtilOCEi.yBAkzBOUzk1yd4b5UL/2tAVFJgm');

--   username: Mirjam, password: password
insert into users(username, password) values ('Mirjam', '$2a$12$Qb.mt09Z7zU20EPy3PtilOCEi.yBAkzBOUzk1yd4b5UL/2tAVFJgm');


insert into users_roles(username, rolename) values('admin', 'ROLE_ADMIN');
insert into users_roles(username, rolename) values('Piet', 'ROLE_MECHANIC');
insert into users_roles(username, rolename) values('Mirjam', 'ROLE_CASHIER');