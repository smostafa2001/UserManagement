CREATE TABLE user_tbl
(
  id bigint NOT NULL,
  name character varying,
  password character varying,
  email character varying,
  birthday date,
  salary double precision,
  CONSTRAINT uid PRIMARY KEY (id )
);
insert into user_tbl (id, name, password, email, birthday, salary) values (1, 'admin', 'admin', 'admin@yahoo.com', '12/09/1990', 12838.00);
