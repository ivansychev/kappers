create table events
(
	id serial not null,
	u_id integer not null
		constraint events_users_id_fk
			references users,
	f_id integer
		constraint events_fixtures_fixture_id_fk
			references fixtures,
	outcome integer,
	coefficient float,
	tokens integer,
	price float
)
;

alter table events owner to postgres
;

