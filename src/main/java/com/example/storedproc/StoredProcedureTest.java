package com.example.storedproc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An internal implementation for the secure store as a procedure call.
 */
public class StoredProcedureTest {

    /**
     * Storing the values in the MY_PERSISTENCE table.
     *
     * @param conn The connection to the database.
     * @param myName The name to use.
     * @param key The key in the store to use.
     * @param values The value to store in the database.
     * @throws SQLException In case an error occurs inserting data into the database.
     */
    public static void insert(Connection conn, String myName, String key,
                              byte[] values) throws SQLException {
        System.out.printf("values inserted: name=%s / key=%s / value=%s%n", myName, key, new String(values));
        PreparedStatement st =
                conn.prepareStatement("insert into MY_PERSISTENCE(my_name, my_key, my_value) values(?,?,?)");
        st.setString(1, myName);
        st.setString(2, key);
        st.setBytes(3, values);
        st.executeUpdate();
        conn.commit();
    }

    /**
     * Reads the value from the MY_PERSISTENCE table.
     *
     * @param conn An instance of the current connection.
     * @param myName The name to use.
     * @param key The key in the store to use.
     * @param retVal The object where the value will be inserted and then returned.
     * @return The read value from the database.
     *
     * @throws SQLException In case an error occurs inserting data into the database.
     */
    public static byte[] get(Connection conn, String myName, String key,
                             byte[] retVal) throws SQLException {
    	System.out.printf("values inserted: name=%s / key=%s / value=%s%n", myName, key);
        PreparedStatement st =
                conn.prepareStatement("select my_value from MY_PERSISTENCE where my_name=? and my_key=?)");
        st.setString(1, myName);
        st.setString(2, key);
        ResultSet rs = st.executeQuery();
        boolean isFirst = rs.first();
        if (isFirst) {
            retVal = rs.getBytes("my_value");
        }
        return retVal;
    }

    /**
     * Deletes values from the MY_PERSISTENCE table.
     *
     * @param conn An instance of the current connection.
     * @param myName The name to use.
     * @param key The key in the store to use.
     * @throws SQLException In case an error occurs inserting data into the database.
     */
    public static void delete(Connection conn, String myName, String key) throws SQLException {
    	System.out.printf("values inserted: name=%s / key=%s / value=%s%n", myName, key);
        PreparedStatement st =
                conn.prepareStatement("delete from MY_PERSISTENCE where my_name=? and my_key=?");
        st.setString(1, myName);
        st.setString(2, key);
        boolean execute = st.execute();
        if (execute) {
            conn.commit();
        }
        System.out.printf("Deleting the store %s and key %s was executed successuflly: %s%n", myName, key, execute);
    }
}
