/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public interface UserDAO {

	public void addUser() throws IOException, CustomServerException;

	public void deleteUser() throws IOException, CustomServerException;

	public void updateUser() throws IOException, CustomServerException;

	public List<User> getAllUsers() throws IOException, CustomServerException;

	public User getUserByID() throws IOException, CustomServerException;

	public String getGoogleID();

	public User getUserByGoogleID() throws IOException;

	public int getUserId();

	public String getName();

	public void setName(String name);

	public void setUserId(int userId);

	void setGoogleId(String googleId);

	void setNotificationEnabled(boolean notificationEnabled);

	boolean isNotificationEnabled();

}
