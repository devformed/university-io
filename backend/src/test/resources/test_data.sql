truncate reservation_ cascade;
truncate lockermat_cell_ cascade;
truncate lockermat_ cascade;

insert into lockermat_ (address_, location_)
values ('A Street', st_geomfromtext('point(10 20)', 4326)),
       ('B Street', st_geomfromtext('point(30 40)', 4326));

insert into lockermat_cell_ (lockermat_id_, size_)
select id_, size_
from lockermat_
cross join lateral unnest(array['S', 'M', 'L']) size_;

insert into reservation_ (cell_id_, from_, to_)
select id_, now() - interval '1' day, now()
from lockermat_cell_;