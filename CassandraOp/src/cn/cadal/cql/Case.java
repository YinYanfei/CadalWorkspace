package cn.cadal.cql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Case {

	public static void main(String[] args) {
		java.sql.Connection con = null;
		try {
			String selectQ = "SELECT \"first\", \"last\" FROM Users WHERE KEY=\"jsmith\"";
			Class.forName("com.impetus.jdbc.cassandra.api.CassandraDriver");
			// con = DriverManager.getConnection("jdbc:cassandra:localhost@9160");
			con = DriverManager.getConnection("jdbc:cassandra:root/Cadal205@localhost:9160/root1");
			// With Statement
			scrollResultset(withStatement(con));
			// With PreparedStatement
			scrollResultset(withPreparedStatement(con, selectQ));
			// Update/INSERT
			withUpdateStatement(con);
			String updateSelect = "SELECT \"firstN\", \"lastN\" FROM Users WHERE KEY=\"jsmith\"";
			scrollResultset(withPreparedStatement(con, updateSelect));
			// Delete
			withDeleteStatement(con);
			scrollResultset(withPreparedStatement(con, updateSelect));
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				con = null;
			}
		}
	}

	private static ResultSet withStatement(Connection con) throws SQLException {
		// String useQ = "USE Keyspace1";
		String selectQ = "SELECT \"first\", \"last\" FROM Users WHERE KEY=\"jsmith\"";
		Statement stmt = con.createStatement();
		// stmt.execute(useQ);
		return stmt.executeQuery(selectQ);
	}

	private static ResultSet withPreparedStatement(Connection con,
			String selectQ) throws SQLException {
		// String useQ = "USE Keyspace1";
		// PreparedStatement statement = con.prepareStatement(useQ);
		// statement.execute();
		PreparedStatement statement = con.prepareStatement(selectQ);
		return statement.executeQuery();
	}

	private static void scrollResultset(ResultSet rSet) throws SQLException {
		while (rSet.next()) {
			System.out.println(rSet.getString(0));
			System.out.println(rSet.getString("last"));
			System.out.println(rSet.getString("lastN"));
		}
	}

	private static void withUpdateStatement(Connection con) throws SQLException {
		String updateQ = "UPDATE Users SET \"firstN\" = \"vivekn\", \"lastN\" = \"mishran\" WHERE KEY = \"jsmith\"";
		PreparedStatement statement = con.prepareStatement(updateQ);
		statement.execute();
	}

	private static void withDeleteStatement(Connection con) throws SQLException {
		String deleteQ = "DELETE \"firstN\", \"lastN\" FROM Users WHERE KEY=\"jsmith\"";
		PreparedStatement statement = con.prepareStatement(deleteQ);
		statement.execute();
	}
}
