CREATE TABLE IF NOT EXISTS categories
(id SERIAL PRIMARY KEY,
name CHARACTER VARYING (100));

CREATE TABLE IF NOT EXISTS expenses
(id SERIAL PRIMARY KEY,
user_id INTEGER,
comment CHARACTER VARYING (100),
amount NUMERIC,
category_id INTEGER,
expense_timestamp TIMESTAMP,
creation_timestamp TIMESTAMP,
CONSTRAINT FK_EXPENSE_ON_CATEGORY_ID FOREIGN KEY (category_id) REFERENCES categories (id));

CREATE TABLE users
(id serial primary key,
login character varying (100),
password character varying (100),
device_id character varying (100));

CREATE TABLE IF NOT EXISTS user_devices
(id SERIAL PRIMARY KEY,
user_id INTEGER,
CONSTRAINT FK_DEVICE_ON_USER_ID FOREIGN KEY (user_id) REFERENCES users (id));

create table principals (
id serial primary key,
username character varying(100),
password character varying(100));

create table roles (
id serial primary key,
name character varying(100),
user_id integer);

CREATE TABLE IF NOT EXISTS incomes
(id SERIAL PRIMARY KEY,
user_id INTEGER,
comment CHARACTER VARYING (100),
amount NUMERIC,
category_id INTEGER,
expense_timestamp TIMESTAMP,
creation_timestamp TIMESTAMP,
CONSTRAINT FK_INCOME_ON_CATEGORY_ID FOREIGN KEY (category_id) REFERENCES categories (id));