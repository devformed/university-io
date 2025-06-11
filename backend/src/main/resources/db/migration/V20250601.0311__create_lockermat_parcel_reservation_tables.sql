create table lockermat_
(
    id_       uuid primary key      not null default gen_random_uuid(),
    address_  text                  not null,
    location_ geometry(point, 4326) not null
);

create table parcel_
(
    id_           uuid primary key not null default gen_random_uuid(),
    lockermat_id_ uuid             not null constraint lockermat__fk references lockermat_ (id_) on delete cascade,
    size_         varchar(10)      not null
);

create table reservation_
(
    id_        uuid primary key            not null default gen_random_uuid(),
    parcel_id_ uuid                        not null constraint parcel__fk references parcel_ (id_) on delete cascade,
    from_      timestamp without time zone not null,
    to_        timestamp without time zone not null
);