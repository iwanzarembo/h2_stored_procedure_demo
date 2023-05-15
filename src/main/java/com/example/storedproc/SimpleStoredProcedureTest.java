package com.example.storedproc;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

import org.h2.tools.RunScript;

public class SimpleStoredProcedureTest {

	private static final String MY_NAME = "myname";
	private static final String MY_KEY = "my key";

	public static void main(String[] args) throws Exception {

		SimpleStoredProcedureTest test = new SimpleStoredProcedureTest();
		test.setUpDb();
		test.addEntry(MY_NAME, MY_KEY, "some value, not important".getBytes(UTF_8));
		byte[] entry = test.getEntry(MY_NAME, MY_KEY);
		if (entry == null) {
			System.err.println("Something went wrong!!!");
		}
		System.out.println("The retrieved value is: " + new String(entry));
	}

	void setUpDb() throws Exception {
		Connection conn = getConnection();
		String jdbcUrl = conn.getMetaData().getURL();
		RunScript.execute(jdbcUrl, "sa", "", "src/main/resources/add_procedure.sql", null, false);
	}

	Connection getConnection() throws Exception {
		return DriverManager.getConnection("jdbc:h2:mem:testdb;TRACE_LEVEL_SYSTEM_OUT=3", "sa", "");
	}

	byte[] getEntry(String storeName, String key) throws Exception {
		try (Connection connection = getConnection()) {
			CallableStatement callableStatement = connection.prepareCall("{call USER_RETRIEVE(?, ?, ?)}");
			callableStatement.setString(1, storeName);
			callableStatement.setString(2, key);
			callableStatement.registerOutParameter(3, Types.VARBINARY);
			callableStatement.execute();
			return callableStatement.getBytes(3);
		}
	}

	void addEntry(String storeName, String key, byte[] value) throws Exception {
		try (Connection connection = getConnection()) {
			CallableStatement callableStatement = connection.prepareCall("{call USER_INSERT(?,?,?)}");
			callableStatement.setString(1, storeName);
			callableStatement.setString(2, key);
			callableStatement.setBytes(3, value);
			callableStatement.executeUpdate();
		}
	}

}
