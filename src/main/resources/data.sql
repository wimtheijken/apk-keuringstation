-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile


insert into roles(rolename) values ('ROLE_USER'), ('ROLE_ADMIN');

--   username: karel, password: appel
insert into users(username, password) values ('karel', '$2a$12$v3hpM1z6mh.ITK9UdFeeiOHOaRzvrlLCCGQc9tyZi718XWXWmLub6');

insert into users_roles(username, rolename) values('karel', 'ROLE_USER');