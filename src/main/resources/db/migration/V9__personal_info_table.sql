create table IF NOT EXISTS personal_info
(
	id serial not null
		constraint personal_info_pkey
			primary key,
	u_id integer not null
		constraint personal_info_users_user_id_fk
			references users,
	photo varchar,
	about varchar
)
;

alter table personal_info owner to postgres
;

create unique index personal_info_id_uindex
	on personal_info (id)
;

