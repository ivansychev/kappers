create table IF NOT EXISTS roles
(
	role_id serial not null
		constraint roles_pkey
			primary key,
	role_name varchar(64) not null
)
;

alter table roles owner to postgres
;

create unique index roles_role_id_uindex
	on roles (role_id)
;
