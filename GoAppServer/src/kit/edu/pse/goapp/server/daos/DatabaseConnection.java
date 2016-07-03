package kit.edu.pse.goapp.server.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;

public class DatabaseConnection implements AutoCloseable {

	private static final String URL = "jdbc:mysql://localhost:3306/PSESoSe16GoGruppe2"
			+ "?user=PSESoSe16User2&password=I059b4x275iYZ8wW&useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private Connection connection;

	public DatabaseConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(URL);
	}

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
			ResultSet resultset = statement.executeQuery(sqlStatement);
			if(handler != null) {
				handler.handleResultSet(resultset);
			}
			resultset.close();
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
			statement.executeUpdate(sqlStatement);
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
			statement.executeUpdate(sqlStatement);
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
