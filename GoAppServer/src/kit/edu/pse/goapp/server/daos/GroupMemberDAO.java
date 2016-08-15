/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Interface group member dao
 */
public interface GroupMemberDAO {

	/**
	 * Add group member
	 * 
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void addMember() throws IOException, CustomServerException;

	/**
	 * Delete group member
	 * 
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void deleteMember() throws IOException, CustomServerException;

	/**
	 * Updates group members
	 * 
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerExceptin
	 */
	public void updateMember() throws IOException, CustomServerException;

	/**
	 * Returns list of all group members
	 * 
	 * @return groupMembers list of all group members
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public List<User> getAllMembers() throws IOException, CustomServerException;

	/**
	 * Returns list of all admins
	 * 
	 * @return admins list of all admins
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public List<User> getAllAdmins() throws IOException, CustomServerException;

	/**
	 * Returns group id
	 * 
	 * @return groupId groupId
	 */
	public int getGroupId();

	/**
	 * Set groupId
	 * 
	 * @param groupId
	 *            groupId
	 */
	public void setGroupId(int groupId);

	/**
	 * Returns userId
	 * 
	 * @return userId userId
	 */
	public int getUserId();

	/**
	 * Set userId
	 * 
	 * @param userId
	 *            userId
	 */
	public void setUserId(int userId);

	/**
	 * Returns if the group member is an admin
	 * 
	 * @return isAdmin true if group member is an admin, else false
	 */
	public boolean isAdmin();

	/**
	 * Set a group member as admin
	 * 
	 * @param isAdmin
	 *            true if group member is an admin, else false
	 */
	public void setAdmin(boolean isAdmin);

}
