create table IF NOT EXISTS fixtures
(
	fixture_id integer not null
		constraint fixtures_pkey
			primary key,
	away_team varchar(255),
	away_team_id integer,
	elapsed integer,
	event_date timestamp ,
	event_timestamp bigint,
	final_score varchar(255),
	first_half_start bigint,
	goals_away_team integer,
	goals_home_team integer,
	halftime_score varchar(255),
	home_team varchar(255),
	home_team_id integer,
	league_id integer,
	penalty varchar(255),
	round varchar(255),
	second_half_start bigint,
	status varchar(255),
	status_short varchar(255)
)
;

alter table fixtures owner to postgres
;

