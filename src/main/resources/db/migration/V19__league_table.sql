create table league
(
  league_id integer not null
    constraint league_pkey
    primary key,
  name varchar(255) not null,
  country varchar(180),
  season varchar(9),
  season_start timestamp,
  season_end timestamp,
  logo varchar(512)
)
;

alter table league owner to postgres
;

create unique index league_league_id_uindex
  on league (league_id)
;

