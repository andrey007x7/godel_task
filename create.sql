create table table_name
(
	id bigserial
		constraint table_name_pk
			primary key,
	name varchar(250) not null
);

create table expense
(
    id bigserial
        constraint expense_pk
            primary key,
    amount double precision not null,
    date date not null,
    name varchar(255) not null,
    category_id bigint
        constraint category_id_fk
            references category
);