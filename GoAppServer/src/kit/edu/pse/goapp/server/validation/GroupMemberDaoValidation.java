/**
 * 
 */
package kit.edu.pse.goapp.server.validation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris class out sources all validation checks
 */
public class GroupMemberDaoValidation {

	/**
	 * 
	 * 
	 */
	public GroupMemberDaoValidation() {
	}

	/**
	 * Checks if an user is group member
	 * 
	 * @param groupMembers
	 *            list of group members
	 * @param userId
	 *            the user's ID who should be checked if he is a member
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void checkIfMember(List<User> groupMembers, int userId) throws CustomServerException {
		boolean isMember = false;
		for (User member : groupMembers) {
			if (member.getId() == userId) {
				isMember = true;
			}
		}
		if (!isMember) {
			throw new CustomServerException("The user is not a member of this group!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
