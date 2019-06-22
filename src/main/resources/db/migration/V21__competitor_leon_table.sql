create table competitor_leon
(
	competitor_id bigint not null
		constraint competitor_leon_pkey
			primary key,
	name varchar(255) not null,
	logo varchar(512)
)
;

alter table competitor_leon owner to postgres
;

create unique index competitor_leon_competitor_id_uindex
	on competitor_leon (competitor_id)
;
