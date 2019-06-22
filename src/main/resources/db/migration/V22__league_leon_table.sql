create table league_leon
(
	league_id bigint not null
		constraint league_leon_pkey
			primary key,
	name varchar(255) not null,
	url varchar(512),
	sport varchar(128)
)
;

alter table league_leon owner to postgres
;

create unique index league_leon_league_id_uindex
	on league_leon (league_id)
;
