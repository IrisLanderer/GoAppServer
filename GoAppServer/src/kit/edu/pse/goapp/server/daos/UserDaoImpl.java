package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;

public class UserDaoImpl implements UserDAO {

	private int userId;
	private String name;
	private String googleId;
	private boolean notificationEnabled;

	private List<Integer> usersIds = new ArrayList<>();

	public UserDaoImpl() {

		super();
	}

	@Override
	public void addUser() throws IOException {
		if (name == null) {
			throw new IllegalArgumentException("A new user must have a name!");
		}
		if (googleId == null) {
			throw new IllegalArgumentException("A new user must have a googleId!");
		}

		try (DatabaseConnection conn = new DatabaseConnection()) {
			String sqlStmt = MessageFormat.format(
					"INSERT INTO users (name, google_id, notifications_enabled) "
							+ "VALUES (''{0}'', ''{1}'', ''{2}'')",
					name, googleId, notificationEnabled == true ? 1 : 0);
			userId = conn.insert(sqlStmt);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteUser() throws IOException {
		if (userId == -1) {
			throw new IllegalArgumentException("A user must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("DELETE FROM users WHERE users_id = ''{0}''", userId);
			connection.delete(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void updateUser() throws IOException {
		if (userId <= 0) {
			throw new IllegalArgumentException("A user must have an ID!");
		}
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"UPDATE users SET name = ''{0}'', notifications_enabled = ''{1}'' WHERE users_id = ''{2}''", name,
					(notificationEnabled == true) ? 1 : 0, userId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public List<User> getAllUsers() throws IOException {
		List<User> users = new ArrayList<>();
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("SELECT users.users_id FROM users", userId);
			connection.select(query, new UsersSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException();
		}
		for (int tmpUserId : usersIds) {
			UserDAO dao = new UserDaoImpl();
			dao.setUserId(tmpUserId);
			User user = dao.getUserByID();
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserByID() throws IOException {
		if (userId <= 0) {
			throw new IllegalArgumentException("A user must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("SELECT u.users_id, u.name, u.notifications_enabled " + "FROM users u  "
					+ "WHERE u.users_id = ''{0}''", userId);
			connection.select(query, new UserSqlSelectionHandler());
			User user = new User(userId, name.toString());
			// was ist mit notificationEnabled
			return user;
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	@Override
	public User getUserByGoogleID() throws IOException {
		if (googleId == null) {
			throw new IllegalArgumentException("A user must have an GoogleID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"SELECT u.users_id, u.name, u.notifications_enabled FROM users u  " + "WHERE u.google_id = ''{0}''",
					googleId);
			connection.select(query, new UserSqlSelectionHandler());
			User user = new User(userId, name.toString());
			return user;
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;

	}

	@Override
	public String getGoogleID() {
		return googleId;
	}

	@Override
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	@Override
	public boolean isNotificationEnabled() {
		return notificationEnabled;
	}

	@Override
	public void setNotificationEnabled(boolean notificationEnabled) {
		this.notificationEnabled = notificationEnabled;
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	private final class UserSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				if (name == null) {
					name = resultSet.getString(2);
				}

				notificationEnabled = resultSet.getBoolean(3);

			}
		}
	}

	private final class UsersSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				usersIds.add(resultSet.getInt(1));

			}
		}
	}

}
