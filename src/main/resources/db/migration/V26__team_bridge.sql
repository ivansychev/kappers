create table team_bridge
(
	id int not null
		constraint team_bridge_pkey
			primary key,
	team_id int not null,
	competitor_id bigint not null
)
;

alter table team_bridge owner to postgres
;

create unique index team_bridge_id_uindex
	on team_bridge (id)
;
