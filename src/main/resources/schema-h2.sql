create table wallet (id bigint generated by default as identity, amount decimal(19, 2), currency varchar(255), primary key (id))