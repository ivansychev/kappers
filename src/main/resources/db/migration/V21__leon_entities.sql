create table competitor_leon
(
	competitor_id bigint not null
		constraint competitor_leon_pkey
			primary key,
	name varchar(255) not null,
	home_away varchar(8),
	type varchar(32),
	logo varchar(512),
	odd_id bigint
)
;

alter table competitor_leon owner to postgres
;

create unique index competitor_leon_competitor_id_uindex
	on competitor_leon (competitor_id)
;

create table league_leon
(
	league_id bigint not null
		constraint league_leon_pkey
			primary key,
	name varchar(255) not null,
	url varchar(512),
	sport_id bigint
)
;

alter table league_leon owner to postgres
;

create unique index league_leon_league_id_uindex
	on league_leon (league_id)
;

create table market_leon
(
	market_id bigint not null
		constraint market_leon_pkey
			primary key,
	name varchar(255) not null,
	open boolean,
	family varchar(255),
	odd_id bigint
)
;

alter table market_leon owner to postgres
;

create unique index market_leon_market_id_uindex
	on market_leon (market_id)
;

create table runner_leon
(
	runner_id bigint not null
		constraint runner_leon_pkey
			primary key,
	name varchar(255) not null,
	open boolean,
	tags varchar,
	price double precision,
	market_id bigint
)
;

alter table runner_leon owner to postgres
;

create unique index runner_leon_runner_id_uindex
	on runner_leon (runner_id)
;

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


