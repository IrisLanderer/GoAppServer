package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;

public interface UserDAO {
	
	public void addUser();
	public void deleteUser();
	public void updateUser();
	public List<User> getAllUsers();
	public User getUserByID();
	public String getGoogleID();
	public boolean getNotificationStatus();
	public User getUserByGoogleID();

}
