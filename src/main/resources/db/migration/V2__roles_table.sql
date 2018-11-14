create table IF NOT EXISTS roles
(
	id serial not null
		constraint roles_pkey
			primary key,
	name varchar(64) not null,
	enabled boolean not null default TRUE
)
;

alter table roles owner to postgres
;

create unique index roles_id_uindex
	on roles (id)
;