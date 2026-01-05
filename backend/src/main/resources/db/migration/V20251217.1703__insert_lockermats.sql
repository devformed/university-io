insert into lockermat_ (address_, location_)
values ('Kamieńskiego Office', st_geomfromtext('point(19.963635273352168 50.0272202438108)', 4326)),
       ('Millennium Hall', st_geomfromtext('point(22.01456717598967 50.02726469603428)', 4326)),
       ('Port Szczecin', st_geomfromtext('point(14.578076 53.418163)', 4326)),
       ('DB Port Szczecin', st_geomfromtext('point(14.581866 53.42414)', 4326)),
       ('Tychy', st_geomfromtext('point(19.027340626981584 50.10415637198125)', 4326)),
       ('Wallbox 1', st_geomfromtext('point(18.148554422891664 51.69098356879579)', 4326)),
       ('Kraków 2', st_geomfromtext('point(19.974273574166773 50.06286510353098)', 4326)),
       ('PNTK', st_geomfromtext('point(18.036534 51.741369)', 4326)),
       ('Serwis Łomża', st_geomfromtext('point(22.05369 53.155931)', 4326)),
       ('Serwis Poznań', st_geomfromtext('point(16.928109494410535 52.41029087660476)', 4326)),
       ('Hu Park Albatros', st_geomfromtext('point(10.53963710940954 43.026423190158596)', 4326)),
       ('La Croix du Vieux Pont', st_geomfromtext('point(3.133358 49.404422)', 4326)),
       ('Frankfurt Vieux', st_geomfromtext('point(8.71316553787534 50.019218915188475)', 4326)),
       ('Tallin', st_geomfromtext('point(24.746462 59.436745)', 4326)),
       ('De Murrin', st_geomfromtext('point(16.936649417506317 52.40813518568709)', 4326)),
       ('Ministerstwo cyfryzacji', st_geomfromtext('point(21.00897739681426 52.23690127542494)', 4326)),
       ('Da Orffo', st_geomfromtext('point(19.959698 50.076287)', 4326)),
       ('White Office', st_geomfromtext('point(19.96288277433635 50.02723655010317)', 4326)),
       ('Serwis Szczecin', st_geomfromtext('point(14.542233732819398 53.42670464590887)', 4326)),
       ('Budimex demo', st_geomfromtext('point(19.79221558710421 49.9779623198535)', 4326)),
       ('Łysoń', st_geomfromtext('point(19.53830497799396 49.88346304848122)', 4326)),
       ('Public Safety Solutions', st_geomfromtext('point(15.44267394869587 47.0104487664257)', 4326)),
       ('Elcamp', st_geomfromtext('point(19.87474782394355 50.034663402849105)', 4326));

update lockermat_
set location_ = st_setsrid(st_makepoint(st_y(location_), st_x(location_)), 4326);

insert into lockermat_cell_ (lockermat_id_, size_)
select id_, size_
from lockermat_ l
cross join lateral unnest(array ['S', 'M', 'L']) size_;