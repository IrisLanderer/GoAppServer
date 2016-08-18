/**
 * 
 */
package kit.edu.pse.goapp.server.validation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class UserValidation {

	/**
	 * checks if user exists at all
	 * 
	 * @param userId
	 *            the user
	 * @return true if user exists
	 * @throws IOException
	 *             if something goes wrong in the database
	 * @throws CustomServerException
	 *             if user doesn't exist
	 */
	public boolean userExists(int userId) throws IOException, CustomServerException {
		UserDAO dao = new UserDaoImpl();
		List<User> users = dao.getAllUsers();
		return userExists(users, userId);
	}

	protected boolean userExists(List<User> users, int userId) throws IOException, CustomServerException {
		for (User tmpUser : users) {
			if (tmpUser.getId() == userId) {
				return true;
			}
		}
		throw new CustomServerException("This user doesn't exist!", HttpServletResponse.SC_BAD_REQUEST);
	}

}
