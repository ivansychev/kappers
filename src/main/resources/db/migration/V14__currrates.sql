create table currency_rate
(
	id serial not null
		constraint currency_rate_pkey
			primary key,
	date date not null,
	charcode varchar(3) not null,
	numcode varchar(3),
	name varchar(255),
	nominal integer ,
	value double precision not null
)
;

alter table currency_rate owner to postgres
;

create unique index currency_rate_id_uindex
	on currency_rate (id)
;

