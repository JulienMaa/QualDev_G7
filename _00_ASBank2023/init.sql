CREATE DATABASE IF NOT EXISTS bankiut;
USE bankiut;

SOURCE ./script/dumpSQL.sql;

CREATE DATABASE IF NOT EXISTS bankiuttest;
USE bankiuttest;

SOURCE ./script/dumpSQL_JUnitTest.sql;
