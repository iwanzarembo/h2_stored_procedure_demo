package com.example.storedproc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.h2.tools.SimpleResultSet;

public class StoredProcedureTest {

  public static void insert(Connection conn, String myName, String key, byte[] values) throws SQLException {
    System.out.printf("values inserted: name=%s / key=%s / value=%s%n", myName, key, new String(values));
    PreparedStatement st = conn.prepareStatement("insert into MY_PERSISTENCE(my_name, my_key, my_value) values(?,?,?)");
    st.setString(1, myName);
    st.setString(2, key);
    st.setBytes(3, values);
    st.executeUpdate();
    conn.commit();
  }

  public static ResultSet getFirst(Connection conn, byte[] retVal, String myName, String key) throws SQLException {
    return get(conn, myName, key, retVal, 1);
  }

  public static ResultSet getSecond(Connection conn, String myName, byte[] retVal, String key) throws SQLException {
    return get(conn, myName, key, retVal, 2);
  }

  public static ResultSet getThird(Connection conn, String myName, String key, byte[] retVal) throws SQLException {
    return get(conn, myName, key, retVal, 3);
  }

  private static ResultSet get(Connection conn, String myName, String key, byte[] retVal,
                               int colIdx) throws SQLException {
    // the retrieval of the metadata is calling this method with null parameters.
    if (myName != null && key != null) {
      System.out.printf("values to read with: name=%s / key=%s%n", myName, key);
      PreparedStatement st = conn.prepareStatement("select my_value from MY_PERSISTENCE where my_name=? and my_key=?");
      st.setString(1, myName);
      st.setString(2, key);
      ResultSet rs = st.executeQuery();
      boolean isFirst = rs.first();
      if (isFirst) {
        retVal = rs.getBytes("my_value");
      }
    }
    // build up the result set with dummy values
    Object[] row = new Object[3];
    SimpleResultSet srs = new SimpleResultSet();
    for (int i = 1; i < 4; i++) {
      if (i == colIdx) {
        srs.addColumn("returnValue", Types.VARBINARY, 10, 0);
        row[colIdx - 1] = retVal;
        continue;
      }
      srs.addColumn("dummy", Types.NULL, 10, 0);
    }
    srs.addRow(row);
    return srs;
  }

  public static byte[] getOutParamIdxOneOnly(Connection conn, byte[] retVal, String myName,
                                             String key) throws SQLException {
    System.out.printf("values inserted: name=%s / key=%s%n", myName, key);
    PreparedStatement st = conn.prepareStatement("select my_value from MY_PERSISTENCE where my_name=? and my_key=?");
    st.setString(1, myName);
    st.setString(2, key);
    ResultSet rs = st.executeQuery();
    boolean isFirst = rs.first();
    if (isFirst) {
      retVal = rs.getBytes("my_value");
    }
    return retVal;
  }

  public static void delete(Connection conn, String myName, String key) throws SQLException {
    System.out.printf("values inserted: name=%s / key=%s / value=%s%n", myName, key);
    PreparedStatement st = conn.prepareStatement("delete from MY_PERSISTENCE where my_name=? and my_key=?");
    st.setString(1, myName);
    st.setString(2, key);
    boolean execute = st.execute();
    if (execute) {
      conn.commit();
    }
    System.out.printf("Deleting the store %s and key %s was executed successuflly: %s%n", myName, key, execute);
  }
}
