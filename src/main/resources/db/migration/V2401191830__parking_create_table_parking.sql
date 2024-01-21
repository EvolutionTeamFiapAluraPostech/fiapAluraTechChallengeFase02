create schema if not exists "parking_management";

create table if not exists "parking_management"."parking"
(
    "id"                    uuid                        not null default gen_random_uuid() primary key,
    "deleted"               boolean                     not null default false,
    "version"               bigint                      not null,
    "created_at"            timestamp without time zone null,
    "created_by"            varchar(255)                null,
    "updated_at"            timestamp without time zone null,
    "updated_by"            varchar(255)                null,
    "vehicle_id"            uuid                        not null
        constraint fk_parking_vehicle references "user_management"."vehicles",
    "user_id"               uuid                        not null
        constraint fk_parking_user references "user_management"."users",
    "latitude"              numeric(10,6)               null,
    "longitude"             numeric(10,6)               null,
    "street"                varchar(255)                not null,
    "neighborhood"          varchar(255)                not null,
    "city"                  varchar(255)                not null,
    "state"                 varchar(50)                 not null,
    "country"               varchar(50)                 not null,
    "parking_state"         varchar(10)                 not null,
    "parking_type"          varchar(10)                 not null,
    "parking_time"          int                         null,
    "initial_parking"       timestamp                   not null,
    "final_parking"         timestamp                   null,
    "parking_payment_id"    uuid                        null
    );

create index if not exists parking_vehicle_user_idx ON parking_management.parking using btree (vehicle_id, user_id);