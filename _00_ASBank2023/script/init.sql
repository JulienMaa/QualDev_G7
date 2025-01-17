CREATE DATABASE IF NOT EXISTS bankiut;
USE bankiut;

SOURCE ./dumpSQL.sql;

CREATE DATABASE IF NOT EXISTS bankiuttest;
USE bankiuttest;

SOURCE ./dumpSQL_JUnitTest.sql;
