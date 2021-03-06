/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Datamodel of Class Group
 */
public class Group {
	private int groupId;
	private String name;
	private List<User> admins;
	private List<User> members;

	/**
	 * Instantiates a new group.
	 *
	 * @param groupId
	 *            the group id
	 * @param name
	 *            the name
	 */
	public Group(int groupId, String name) {
		this.groupId = groupId;
		this.name = name;
		admins = new ArrayList<User>();
		members = new ArrayList<User>();
	}

	/**
	 * Adds the admin.
	 *
	 * @param user
	 *            the user
	 */
	public void addAdmin(User user) {
		admins.add(user);
	}

	/**
	 * Adds the group member.
	 *
	 * @param user
	 *            the user
	 */
	public void addGroupMember(User user) {
		members.add(user);
	}

	/**
	 * checks if user is member of this group
	 * 
	 * @param user
	 *            the user
	 * @throws CustomServerException
	 *             if user is not member of this group
	 * @throws IOException
	 *             if something goes wrong in the database
	 */
	public void isMember(User user) throws CustomServerException, IOException {
		GroupMemberDAO groupMemberDao = new GroupMemberDaoImpl();
		groupMemberDao.setGroupId(groupId);
		groupMemberDao.setUserId(user.getId());
		List<User> tmpMembers = groupMemberDao.getAllMembers();
		isMember(user, tmpMembers);
	}

	protected void isMember(User user, List<User> tmpMembers) throws CustomServerException, IOException {
		if (!checkMemberAndAdmin(user, tmpMembers)) {
			throw new CustomServerException("The user has to be member of this group to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	/**
	 * checks if user is admin of this group
	 * 
	 * @param user
	 *            the user
	 * @throws CustomServerException
	 *             if user is not admin of this group
	 * @throws IOException
	 *             if something goes wrong in the database
	 */
	public void isAdmin(User user) throws CustomServerException, IOException {
		GroupMemberDAO groupMemberDao = new GroupMemberDaoImpl();
		groupMemberDao.setGroupId(groupId);
		groupMemberDao.setUserId(user.getId());
		List<User> tmpAdmins = groupMemberDao.getAllAdmins();
		isAdmin(user, tmpAdmins);

	}

	protected void isAdmin(User user, List<User> tmpAdmins) throws CustomServerException {
		if (!checkMemberAndAdmin(user, tmpAdmins)) {
			throw new CustomServerException("The user has to be admin of this group to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	/**
	 * Checks if an user is authorized as group member or group admin.
	 * 
	 * @param user
	 *            user
	 * @param users
	 *            list of all users
	 * @return isAutorised true if user is authorized as member or admin, else
	 *         false
	 */
	private boolean checkMemberAndAdmin(User user, List<User> users) {
		boolean isAuthorized = false;
		for (User tmpUser : users) {
			if (user.getId() == tmpUser.getId()) {
				isAuthorized = true;
			}
		}
		return isAuthorized;
	}

	/**
	 * Gets the admins.
	 *
	 * @return the admins
	 */
	public List<User> getAdmins() {
		return admins;
	}

	/**
	 * Gets the group members.
	 *
	 * @return the group members
	 */
	public List<User> getGroupMembers() {
		return members;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return groupId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the group members.
	 *
	 * @param members
	 *            the new group members
	 */
	public void setGroupMembers(List<User> members) {
		this.members = members;
	}

	/**
	 * Sets the admins.
	 *
	 * @param admins
	 *            the new admins
	 */
	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		// Class name is Employ & have lastname
		Group g = (Group) obj;
		if (g.getAdmins().equals(admins) && g.getGroupMembers().equals(members) && g.getId() == groupId
				&& g.getName().equals(name)) {
			return true;
		}
		return false;
	}

}