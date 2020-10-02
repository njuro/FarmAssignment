alter table country
    rename column iso3 to code;

alter table country
    rename column wkb to borders;

alter index country_wkb_idx rename to country_border_idx;