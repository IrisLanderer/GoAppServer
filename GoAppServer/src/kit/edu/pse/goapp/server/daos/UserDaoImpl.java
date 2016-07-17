/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

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
		try {
			this.addUser(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void addUser(DatabaseConnection conn) throws IOException, CustomServerException {
		if (name == null || name.equals("")) {
			throw new CustomServerException("A new user must have a name!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (googleId == null || googleId.equals("")) {
			throw new CustomServerException("A new user must have a googleId!", HttpServletResponse.SC_BAD_REQUEST);
		}

		try {
			String sqlStmt = MessageFormat.format(
					"INSERT INTO users (name, google_id, notifications_enabled) "
							+ "VALUES (''{0}'', ''{1}'', ''{2}'')",
					name, googleId, notificationEnabled == true ? 1 : 0);
			userId = conn.insert(sqlStmt);
			if (userId <= 0) {
				throw new CustomServerException("The UserID wasn't assigned to a value!",
						HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteUser() throws IOException {
		try {
			this.deleteUser(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void deleteUser(DatabaseConnection connection) throws IOException, CustomServerException {
		if (userId <= 0) {
			throw new CustomServerException("A user must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String queryUser = MessageFormat.format("DELETE FROM users WHERE users_id = ''{0}''", userId);
			String queryGroupMembers = MessageFormat.format("DELETE FROM group_members WHERE users_id = ''{0}''",
					userId);
			String queryParticipant = MessageFormat.format("DELETE FROM participants WHERE users_id = ''{0}''", userId);
			connection.delete(queryUser);
			connection.delete(queryGroupMembers);
			connection.delete(queryParticipant);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void updateUser() throws IOException {
		try {
			this.updateUser(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void updateUser(DatabaseConnection connection) throws IOException, CustomServerException {
		if (userId <= 0) {
			throw new CustomServerException("A user must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (name == null || name.equals("")) {
			throw new CustomServerException(
					"You cannot change the name of a user to null, because a user must have a name!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"UPDATE users SET name = ''{0}'', notifications_enabled = ''{1}'' WHERE users_id = ''{2}''", name,
					(notificationEnabled == true) ? 1 : 0, userId);
			connection.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public List<User> getAllUsers() throws IOException {
		List<User> users = null;
		try {
			users = getAllUsers(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return users;
	}

	protected List<User> getAllUsers(DatabaseConnection connection) throws IOException, CustomServerException {
		List<User> users = new ArrayList<>();
		try {
			String query = MessageFormat.format("SELECT users.users_id FROM users order by users.name asc", userId);
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
		User user = null;
		try {
			user = getUserByID(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return user;
	}

	protected User getUserByID(DatabaseConnection connection) throws IOException, CustomServerException {
		if (userId <= 0) {
			throw new CustomServerException("A user must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format("SELECT u.users_id, u.name, u.notifications_enabled " + "FROM users u  "
					+ "WHERE u.users_id = ''{0}''", userId);
			connection.select(query, new UserSqlSelectionHandler());
			User user = new User(userId, name.toString());
			user.setNotificationEnabled(notificationEnabled);
			return user;
		} catch (Throwable e) {
			if (e.getCause().getClass() == CustomServerException.class) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			throw new IOException(e);
		}
	}

	@Override
	public User getUserByGoogleID() throws IOException {
		User user = null;
		try {
			user = getUserByGoogleID(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return user;
	}

	// TODO
	protected User getUserByGoogleID(DatabaseConnection connection) throws IOException, CustomServerException {
		if (googleId == null || googleId.equals("")) {
			throw new CustomServerException("A user must have an GoogleID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"SELECT u.users_id, u.name, u.notifications_enabled FROM users u  " + "WHERE u.google_id = ''{0}''",
					googleId);
			connection.select(query, new GoogleIdSqlSelectionHandler());
			if (userId <= 0 && name == null) {
				return null;
			}
			User user = new User(userId, name.toString());
			user.setNotificationEnabled(notificationEnabled);
			return user;
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	@Override
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

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public String getName() {
		return name;
	}

	private final class UserSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {
			boolean resultEmpty = true;
			while (resultSet.next()) {
				resultEmpty = false;
				userId = resultSet.getInt(1);
				name = resultSet.getString(2);
				notificationEnabled = resultSet.getBoolean(3);

			}
			if (resultEmpty) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

	private final class GoogleIdSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {
			while (resultSet.next()) {
				userId = resultSet.getInt(1);
				name = resultSet.getString(2);
				notificationEnabled = resultSet.getBoolean(3);

			}
		}
	}

	private final class UsersSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {
			boolean resultEmpty = true;
			while (resultSet.next()) {
				resultEmpty = false;
				usersIds.add(resultSet.getInt(1));
			}
			if (resultEmpty) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

}
