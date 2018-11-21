create table IF NOT EXISTS balance
(
	id serial not null,
	u_id integer not null
		constraint balance_users_user_id_fk
		references users
		on delete cascade,
	balance float ,
	currency varchar
)
;

create unique index balance_id_uindex
	on balance (id)
;

create unique index balance_user_id_uindex
	on balance (u_id)
;

