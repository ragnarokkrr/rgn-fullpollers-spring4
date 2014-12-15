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

insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (101, 'Equip101', 1, 'Rio de Janeito', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (102, 'Equip102', 3, 'Petropolis', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (103, 'Equip103', 3, 'Petropolis', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (104, 'Equip104', 3, 'Petropolis', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (105, 'Equip105', 3, 'Petropolis', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (106, 'Equip106', 3, 'Petropolis', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (107, 'Equip107', 3, 'Petropolis', 'RJ');
insert into EQUIPMENT (id, name, city_id, city_name, city_state) values (108, 'Equip108', 3, 'Petropolis', 'RJ');


CREATE TABLE SILO (
    id BIGINT NOT NULL,
    name varchar (100) not null,
    description varchar (250) not null,
    state varchar (2) not null
);

insert into SILO (id, name, description, state) values (2, 'Rio Silo', 'Equipments in RIO', 'RJ');
