alter table field
    rename column geom to borders;

alter index field_geom_idx rename to field_borders_idx;