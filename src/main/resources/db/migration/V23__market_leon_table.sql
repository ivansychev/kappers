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
