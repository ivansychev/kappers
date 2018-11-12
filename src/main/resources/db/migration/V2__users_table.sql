create table users
(
	user_id serial not null
		constraint users_pkey
		primary key,
	user_name varchar(128),
	password varchar(256) not null,
	name varchar(128),
	email varchar(64),
	date_of_birth timestamp,
	role_id integer,
	date_of_registration timestamp,
	isblocked boolean default false,
	currency varchar(3),
	lang varchar(16)
)
;

alter table users owner to postgres
;

create unique index users_user_id_uindex
	on users (user_id)
;

create unique index users_user_name_uindex
	on users (user_name)
;
