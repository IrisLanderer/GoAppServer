/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection implements AutoCloseable {

	private static final String URL = "jdbc:mysql://localhost:3306/PSESoSe16GoGruppe2"
			+ "?user=PSESoSe16User2&password=I059b4x275iYZ8wW&useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private Connection connection;

	public DatabaseConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(URL);
	}

	/**
	 * executes SQL INSERT query
	 * 
	 * @param sqlStatement
	 *            INSERT query for the database
	 * @return the ID which is automatically created in the database
	 * @throws Exception
	 *             if sqlStatement isn't correct
	 * @author Iris
	 * @version 1.0
	 */
	public int insert(String sqlStatement) throws Exception {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(sqlStatement, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = statement.getGeneratedKeys();
			int key = -1;
			while (resultSet.next()) {
				key = resultSet.getInt(1);
			}
			return key;
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new Exception(e);
				}
			}
		}
	}

	public void select(String sqlStatement, SqlSelectHandler handler) throws Exception {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			if (handler != null) {
				handler.handleResultSet(resultSet);
			}
			resultSet.close();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new Exception(e);
				}
			}
		}
	}

	public void delete(String sqlStatement) throws Exception {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			int affectedRows = statement.executeUpdate(sqlStatement);
			if (affectedRows == 0) {
				throw new IllegalArgumentException("This entry doesn't exist!");
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new Exception(e);
				}
			}
		}
	}

	public void update(String sqlStatement) throws Exception {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			int affectedRows = statement.executeUpdate(sqlStatement);
			if (affectedRows == 0) {
				throw new IllegalArgumentException("This entry doesn't exist!");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void close() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}
}
