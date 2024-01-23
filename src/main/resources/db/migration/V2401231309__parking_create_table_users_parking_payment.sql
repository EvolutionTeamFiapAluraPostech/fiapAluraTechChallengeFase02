create table if not exists "user_management"."users_payment_method"
(
    "id"                    uuid                        not null default gen_random_uuid() primary key,
    "deleted"               boolean                     not null default false,
    "version"               bigint                      not null,
    "created_at"            timestamp without time zone null,
    "created_by"            varchar(255)                null,
    "updated_at"            timestamp without time zone null,
    "updated_by"            varchar(255)                null,
    "payment_method"        varchar(20)                 not null
);