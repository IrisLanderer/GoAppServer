/**
 * 
 */
package kit.edu.pse.goapp.server.creating_obj_with_dao;

import java.io.IOException;

import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class UserWithDao {

	/**
	 * Creates an user
	 * 
	 * @param userId
	 *            userId
	 * @return user user
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public User createUserWithDao(int userId) throws IOException, CustomServerException {
		UserDAO userDao = new UserDaoImpl();
		userDao.setUserId(userId);
		User user = userDao.getUserByID();
		return user;
	}

}
