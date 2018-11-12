create table IF NOT EXISTS
 history
(
	id serial not null
		constraint history_pkey
		primary key,
	u_id integer,
	changes varchar
)
;

create unique index history_id_uindex
	on history (id)
;

