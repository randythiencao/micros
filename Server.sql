DROP USER notyelp CASCADE;
CREATE USER notyelp
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 100M ON users;
GRANT connect to notyelp;
GRANT resource to notyelp;
GRANT create session TO notyelp;
GRANT create table TO notyelp;
GRANT create view TO notyelp;
GRANT unlimited tablespace to notyelp;
conn notyelp/p4ssw0rd

DROP TABLE USER_ROLES;
DROP TABLE USERS;
DROP TABLE USER_STATE;

DROP SEQUENCE USER_SEQ;

create table users
(
    USER_ID NUMBER PRIMARY KEY, 
    USERNAME VARCHAR2(50), 
	PASSWORD VARCHAR2(50), 
    SALT RAW(16),
	FIRSTNAME VARCHAR2(100), 
	LASTNAME VARCHAR2(100), 
	EMAIL VARCHAR2(150), 
	ROLE_ID NUMBER
    );
create table USER_ROLES
(
    USER_ROLE_ID NUMBER PRIMARY KEY, 
    ROLE VARCHAR2(10) NOT NULL 
);

create table user_state
(
	 USER_ID NUMBER, 
	 CREATED_DT TIMESTAMP,
	 URL VARCHAR2(400),
	 primary key (USER_ID, URL)
);


CREATE SEQUENCE USER_SEQ start with 1;
