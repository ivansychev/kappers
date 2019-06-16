create table sport_leon
(
	sport_id bigint not null
		constraint sport_leon_pkey
			primary key,
	name varchar(255) not null,
	betline_name varchar(255),
	betline_combination varchar(255)
)
;

alter table sport_leon owner to postgres
;

create unique index sport_leon_sport_id_uindex
	on sport_leon (sport_id)
;
