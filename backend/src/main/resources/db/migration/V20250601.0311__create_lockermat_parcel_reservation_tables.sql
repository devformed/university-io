create sequence lockermat_id_seq_ start with 1 increment by 1;
create table lockermat_
(
    id_       bigint primary key default nextval('lockermat_id_seq_'::regclass),
    address_  text                  not null,
    location_ geometry(point, 4326) not null
);

create sequence lockermat_cell_id_seq_ start with 1 increment by 1;
create table lockermat_cell_
(
    id_           bigint primary key default nextval('lockermat_cell_id_seq_'::regclass),
    lockermat_id_ bigint      not null
        constraint lockermat__fk references lockermat_ (id_) on delete cascade,
    size_         varchar(10) not null
);

create sequence reservation_id_seq_ start with 1 increment by 1;
create table reservation_
(
    id_      bigint primary key default nextval('reservation_id_seq_'::regclass),
    cell_id_ bigint                      not null
        constraint lockermat_cell__fk references lockermat_cell_ (id_) on delete cascade,
    from_    timestamp without time zone not null,
    to_      timestamp without time zone not null
);