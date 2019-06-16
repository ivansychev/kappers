create table odds_leon
(
	odd_id bigint not null
		constraint odds_leon_pkey
			primary key,
	name varchar(255) not null,
	league_id bigint not null,
	kickoff timestamp,
	last_updated timestamp,
	open boolean,
	url varchar(512)
)
;

alter table odds_leon owner to postgres
;

create unique index odds_leon_odds_id_uindex
	on odds_leon (odd_id)
;
