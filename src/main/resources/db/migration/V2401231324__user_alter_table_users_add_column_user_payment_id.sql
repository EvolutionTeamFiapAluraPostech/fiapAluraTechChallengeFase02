alter table "user_management"."users" drop column if exists payment_method_id;

alter table "user_management"."users" add column payment_method_id uuid null;

alter table "user_management"."users" drop constraint if exists fk_user_payment;

alter table "user_management"."users" add constraint fk_user_payment
    foreign key (payment_method_id) references "user_management"."users_payment_method" (id);