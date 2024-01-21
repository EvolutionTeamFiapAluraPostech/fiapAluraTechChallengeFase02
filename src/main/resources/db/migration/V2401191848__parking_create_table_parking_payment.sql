create table if not exists "parking_management"."parking_payment"
(
    "id"                    uuid                        not null default gen_random_uuid() primary key,
    "deleted"               boolean                     not null default false,
    "version"               bigint                      not null,
    "created_at"            timestamp without time zone null,
    "created_by"            varchar(255)                null,
    "updated_at"            timestamp without time zone null,
    "updated_by"            varchar(255)                null,
    "payment_state"         varchar(20)                 not null,
    "payment_value"         numeric(10,2)               not null
);

alter table "parking_management"."parking" drop constraint if exists fk_parking_payment;

alter table "parking_management"."parking" add constraint fk_parking_payment
    foreign key (parking_payment_id) references "parking_management"."parking_payment" (id);
