CREATE TABLE EQUIPMENT (
    id BIGINT NOT NULL,
    name VARCHAR (250) not null,
    description VARCHAR (250) not null,
    city_id BIGINT not null,
    city_name VARCHAR(100) not null,
    city_state VARCHAR(2) not null,
	equip_model_id BIGINT not null,
    equip_model_name varchar(255) not null,
    equip_model_code varchar(50) not null
);

ALTER TABLE EQUIPMENT
ADD CONSTRAINT EQUIPMENT_PK
PRIMARY KEY(ID);

CREATE INDEX EQUIPMENT_IDX01_CITY ON EQUIPMENT(city_name);

insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (1, 'Equip1', 'Equip1 - R2D2 - POA', 1, 'Porto Alegre', 'RS', 1, 'Model R2D2', 'R2D2');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (2, 'Equip2', 'Equip2 - C3PO - Canoas', 3, 'Canoas', 'RS', 2, 'Model C3PO', 'C3PO');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (3, 'Equip3', 'Equip3 - T800 - Canoas', 3, 'Canoas', 'RS', 3, 'Model T-800', 'T-800');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (4, 'Equip4', 'Equip4 - T1000 - Canoas', 3, 'Canoas', 'RS', 4, 'Model T-1000', 'T-1000');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (5, 'Equip5', 'Equip5 - T800 - Canoas', 3, 'Canoas', 'RS', 3, 'Model T-800', 'T-800');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (6, 'Equip6', 'Equip6 - T1000 - Canoas', 3, 'Canoas', 'RS', 4, 'Model T-1000', 'T-1000');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (7, 'Equip7', 'Equip7 - T800 - Canoas', 3, 'Canoas', 'RS', 3, 'Model T-800', 'T-800');
insert into EQUIPMENT (id, name, description, city_id, city_name, city_state, equip_model_id, equip_model_name, equip_model_code)
        values (8, 'Equip8', 'Equip8 - T1000 - Canoas', 3, 'Canoas', 'RS', 4, 'Model T-1000', 'T-1000');


CREATE TABLE SILO (
    id BIGINT NOT NULL,
    name varchar (100) not null,
    description varchar (250) not null,
    state varchar (2) not null
);

insert into SILO (id, name, description, state) values (1, 'RS Silo', 'Equipments in RS', 'RS');
