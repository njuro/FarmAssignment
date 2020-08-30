drop table if exists field;
drop table if exists farm;
drop table if exists country;

create table farm
(
    id         uuid                     not null,
    name       varchar(255)             not null,
    note       varchar(255),
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now(),
    primary key (id)
);

create table field
(
    id         uuid                     not null,
    name       varchar(255)             not null,
    farm_id    uuid                     not null,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now(),
    geom       geometry(Polygon, 4326)  not null,
    primary key (id)
);

create table country
(
    iso3 varchar(3),
    wkb  geometry(MultiPolygon, 4326),
    name varchar(50),
    primary key (iso3)
);

create index country_wkb_idx
    on country USING GIST (wkb);


alter table if exists field
    add constraint fk_field_farm
        foreign key (farm_id)
            references farm;