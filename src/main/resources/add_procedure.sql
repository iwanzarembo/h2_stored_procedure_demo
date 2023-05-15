CREATE TABLE IF NOT EXISTS MY_PERSISTENCE  (
    MY_NAME  VARCHAR(530) NOT NULL,
	MY_KEY   VARCHAR(1024) NOT NULL,
	MY_VALUE LONGVARBINARY,
	constraint INT_PERS_PK primary key (MY_NAME)
);

CREATE ALIAS IF NOT EXISTS USER_INSERT   FOR "com.example.storedproc.StoredProcedureTest.insert";
CREATE ALIAS IF NOT EXISTS USER_RETRIEVE FOR "com.example.storedproc.StoredProcedureTest.get";
CREATE ALIAS IF NOT EXISTS USER_DELETE   FOR "com.example.storedproc.StoredProcedureTest.delete";
