package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;


import kit.edu.pse.goapp.server.datamodels.User;

public class UserDaoImpl implements UserDAO {

	private int userId;
	private String name;
	private String googleId;
	private boolean notificationEnabled;

	public UserDaoImpl() {
		
		super();
	}


	@Override
	public void addUser() throws IOException {
		if (name == null) {
			throw new IllegalArgumentException("A new user must have a name!");
		}
//		if (googleId == null) {
//			throw new IllegalArgumentException("A new user must have a googleId!");
//		}

		try (DatabaseConnection conn = new DatabaseConnection()) {
			String sqlStmt = MessageFormat.format("INSERT INTO users (name, google_id, notifications_enabled) "
					+ "VALUES (''{0}'', ''{1}'', ''{2}'')", name, googleId, notificationEnabled == true ? 1 : 0);
			userId = conn.insert(sqlStmt);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
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
		if (userId == -1) {
			throw new IllegalArgumentException("A user must have an ID!");
		}
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format("UPDATE users SET name = ''{0}'', notifications_enabled = ''{1}''"
					+ " WHERE users_id = ''{2}''", name, notificationEnabled == true ? 1 : 0, userId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}


	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGoogleID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getNotificationStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserByGoogleID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void setUserId(int userId) {
		this.userId = userId;
		
	}

}
