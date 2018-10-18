create table roles
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



INSERT INTO public.roles (role_id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (role_id, role_name) VALUES (2, 'ROLE_USER');
INSERT INTO public.roles (role_id, role_name) VALUES (3, 'ROLE_KAPPER');