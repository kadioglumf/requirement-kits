create table transportation_order
(
    id                        bigint       not null
        primary key,
    creation_date             timestamp    not null,
    last_update_date          timestamp,
    order_id                  varchar(255) not null
        constraint uk_sfqyu73avtepn991qkur314wx
            unique,
    user_id                   bigint       not null,
    transportation_request_id bigint       not null
);

alter table transportation_order
    owner to postgres;

create table transportation_request
(
    id                        bigint       not null
        primary key,
    creation_date             timestamp    not null,
    last_update_date          timestamp,
    is_approved               boolean,
    number_of_sales           integer      not null,
    transportation_request_id varchar(255) not null
        constraint uk_jetotuv3oya3f8dmbbjx85gj9
            unique

);

alter table transportation_request
    owner to postgres;

alter table if exists transportation_order
add constraint fkf4omvr0g5hn41npiijfy9nefn
foreign key (transportation_request_id)
references transportation_request;
