create table IF NOT EXISTS stat
(
	id serial not null
		constraint stat_pkey
primary key,
	u_id integer
		constraint stat_users_user_id_fk
			references users,
	issue_type integer,
	content varchar
)
;
 alter table stat owner to postgres;
create unique index stat_id_uindex
	on stat (id);