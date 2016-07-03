package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;

public interface UserDAO {
	
	public void addUser() throws IOException;
	public void deleteUser() throws IOException;
	public void updateUser() throws IOException;
	public List<User> getAllUsers();
	public User getUserByID();
	public String getGoogleID();
	public boolean getNotificationStatus();
	public User getUserByGoogleID();
	public int getUserId();
	public String getName();
	public void setName(String name);
	public void setUserId(int userId);
	


}
