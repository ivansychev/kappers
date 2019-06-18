create table runner_leon
(
	runner_id bigint not null
		constraint runner_leon_pkey
			primary key,
	name varchar(255) not null,
	open boolean,
	tags varchar,
	price double precision,
	market_id bigint,
	odd_id bigint
)
;

alter table runner_leon owner to postgres
;

create unique index runner_leon_runner_id_uindex
	on runner_leon (runner_id)
;
