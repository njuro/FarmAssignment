create table if not exists farm
(
    id         uuid                     not null,
    name       varchar(255)             not null,
    note       varchar(255),
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now(),
    primary key (id)
);

create table if not exists field
(
    id         uuid                     not null,
    name       varchar(255)             not null,
    farm_id    uuid                     not null,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now(),
    geom       geometry(Polygon, 4326)  not null,
    primary key (id)
);

create table if not exists country
(
    iso3 varchar(3),
    wkb  geometry(MultiPolygon, 4326),
    name varchar(50),
    primary key (iso3)
);

create index if not exists country_wkb_idx
    on country USING GIST (wkb);


alter table if exists field
    drop constraint if exists fk_field_farm;
alter table if exists field
    add constraint fk_field_farm
        foreign key (farm_id)
            references farm;