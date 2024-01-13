create table if not exists "user_management"."vehicles"
(
    "id"            uuid                        not null default gen_random_uuid() primary key,
    "deleted"       boolean                     not null default false,
    "version"       bigint                      not null,
    "created_at"    timestamp without time zone null,
    "created_by"    varchar(255)                null,
    "updated_at"    timestamp without time zone null,
    "updated_by"    varchar(255)                null,
    "description"   varchar(500)                not null,
    "license_plate" varchar(500)                not null,
    "color"         varchar(14)                 not null
);

create index if not exists vehicles_license_plate_idx ON user_management.vehicles using btree (license_plate);