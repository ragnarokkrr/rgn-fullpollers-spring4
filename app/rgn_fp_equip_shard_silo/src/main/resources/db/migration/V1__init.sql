CREATE TABLE EQUIPMENT (
    id BIGINT NOT NULL,
    name VARCHAR (250) not null,
    city_id BIGINT not null,
    city_name VARCHAR(100) not null,
    city_state VARCHAR(2) not null
);

ALTER TABLE EQUIPMENT
ADD CONSTRAINT EQUIPMENT_PK
PRIMARY KEY(ID);

CREATE INDEX EQUIPMENT_IDX01_CITY ON EQUIPMENT(city_name);

insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (1, 'Equip1', 1, 'Porto Alegre', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (2, 'Equip2', 3, 'Canoas', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (3, 'Equip3', 3, 'Canoas', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (4, 'Equip4', 3, 'Canoas', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (5, 'Equip5', 3, 'Canoas', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (6, 'Equip6', 3, 'Canoas', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (7, 'Equip7', 3, 'Canoas', 'RS');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (8, 'Equip8', 3, 'Canoas', 'RS');


CREATE TABLE SILO (
    id BIGINT NOT NULL,
    name varchar (100) not null,
    description varchar (250) not null,
    state varchar (2) not null
);

insert into SILO (id, name, description, state) values (1, 'RS Silo', 'Equipments in RS', 'RS');
