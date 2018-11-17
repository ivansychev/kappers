create table IF NOT EXISTS events
(
	id serial not null,
	u_id integer not null
		constraint events_users_user_id_fk
		references users
		on delete cascade,
	outcome integer,
	coefficient float ,
	tokens integer,
	price float
)
;

create unique index events_id_uindex
	on kapper_info (id)
;

create unique index events_user_id_uindex
	on kapper_info (u_id)
;
