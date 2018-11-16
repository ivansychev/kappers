create table IF NOT EXISTS users
(
	id serial not null
		constraint users_pkey
		primary key,
	user_name varchar(128),
	password varchar(256) not null,
	name varchar(128),
	email varchar(64),
	date_of_birth timestamp,
	role_id integer not null
		constraint users_roles_id_fk
		references roles,
	date_of_registration timestamp,
	isblocked boolean not null default false,
	currency varchar(3),
	lang varchar(16)
)
;

alter table users owner to postgres
;

create unique index users_id_uindex
	on users (id)
;

create unique index users_user_name_uindex
	on users (user_name)
;

create index users_role_id_index
	on users (role_id)
;
