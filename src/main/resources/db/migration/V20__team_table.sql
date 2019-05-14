create table team
(
  team_id integer not null
    constraint team_pkey
    primary key,
  name varchar(255) not null,
  code varchar(8),
  logo varchar(512)
)
;

alter table team owner to postgres
;

create unique index team_team_id_uindex
  on team (team_id)
;

