/**
 * 
 */
package kit.edu.pse.goapp.server.validation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * class out sources all validation checks
 * 
 * @author Iris
 */
public class GroupMemberValidation {

	/**
	 * 
	 * 
	 */
	public GroupMemberValidation() {
	}

	/**
	 * Checks if an user is group member
	 * 
	 * @param groupMembers
	 *            list of group members
	 * @param userId
	 *            the user's ID who should be checked if he is a member
	 * @throws CustomServerException
	 *             if this user is not a member of this group
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

	/**
	 * checks, if this user is already a member of this group
	 * 
	 * @param groupMembers
	 *            all group members in this group
	 * @param userId
	 *            the user who has to be added to this group
	 * @throws CustomServerException
	 *             if user is already member of this group
	 */

	public void checkIfAlreadyMember(List<User> groupMembers, int userId) throws CustomServerException {
		for (User member : groupMembers) {
			if (member.getId() == userId) {
				throw new CustomServerException("The user is already a member of this group!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

}
