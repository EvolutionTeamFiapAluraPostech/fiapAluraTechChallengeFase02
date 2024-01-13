create table if not exists "user_management"."users_vehicles"
(
    "user_id"        uuid                not null unique,
    "vehicles_id"    uuid                not null unique
)