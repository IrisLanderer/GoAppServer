package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;

public class UserDaoImpl implements UserDAO {

	private int userId;
	private String name;
	private double googleId;
	private boolean notificationEnabled;

	public UserDaoImpl(int userId, String name, double googleId, boolean notificationEnabled) {
		super();
		this.userId = userId;
		this.name = name;
		this.googleId = googleId;
		this.notificationEnabled = notificationEnabled;
	}

	@Override
	public void addUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser() {
		// TODO Auto-generated method stub

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

}
