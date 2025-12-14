truncate reservation_ cascade;
truncate parcel_ cascade;
truncate lockermat_ cascade;

insert into lockermat_ (address_, location_)
values ('a street', st_geomfromtext('point(10 20)', 4326)),
       ('b street', st_geomfromtext('point(30 40)', 4326));

insert into parcel_ (lockermat_id_, size_)
select id_, size_
from lockermat_ l
         cross join lateral (unnest(array['S', 'M', 'L'])) size_;

insert into reservation (parcel_id_, from_, to_)
select id_, now() - interval '1' day, now()
from parcel_ p;