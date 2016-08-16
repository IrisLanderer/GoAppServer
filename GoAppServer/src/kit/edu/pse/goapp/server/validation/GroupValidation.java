/**
 * 
 */
package kit.edu.pse.goapp.server.validation;

import java.io.IOException;

import kit.edu.pse.goapp.server.creating_obj_with_dao.GroupWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class GroupValidation {
	private GroupWithDao groupWithDao = new GroupWithDao();
	private UserWithDao userWithDao = new UserWithDao();

	/**
	 * Checks if user is group member and admin
	 * 
	 * @param userId
	 *            userId
	 * @param groupId
	 *            groupId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void checkIfUserIsMemberAndAdmin(int userId, int groupId) throws IOException, CustomServerException {
		User user = userWithDao.createUserWithDao(userId);
		Group groupCheckingAuthorization = groupWithDao.createGroupWithDao(groupId);
		groupCheckingAuthorization.isMember(user);
		groupCheckingAuthorization.isAdmin(user);
	}

}
