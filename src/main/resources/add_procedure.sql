CREATE TABLE IF NOT EXISTS MY_PERSISTENCE  (
    MY_NAME  VARCHAR(530) NOT NULL,
	MY_KEY   VARCHAR(1024) NOT NULL,
	MY_VALUE LONGVARBINARY,
	constraint INT_PERS_PK primary key (MY_NAME)
);

CREATE ALIAS IF NOT EXISTS USER_INSERT        FOR "com.example.storedproc.StoredProcedureTest.insert";
CREATE ALIAS IF NOT EXISTS USER_DELETE        FOR "com.example.storedproc.StoredProcedureTest.delete";
CREATE ALIAS IF NOT EXISTS USER_GET_FIRST     FOR "com.example.storedproc.StoredProcedureTest.getFirst";
CREATE ALIAS IF NOT EXISTS USER_GET_SECOND    FOR "com.example.storedproc.StoredProcedureTest.getSecond";
CREATE ALIAS IF NOT EXISTS USER_GET_THIRD     FOR "com.example.storedproc.StoredProcedureTest.getThird";
CREATE ALIAS IF NOT EXISTS USER_GET_FIRSTONLY FOR "com.example.storedproc.StoredProcedureTest.getOutParamIdxOneOnly";
