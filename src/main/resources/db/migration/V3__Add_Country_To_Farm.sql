alter table farm
    add country varchar(3) not null;

alter table farm
    add constraint fk_farm_country
        foreign key (country)
            references country;