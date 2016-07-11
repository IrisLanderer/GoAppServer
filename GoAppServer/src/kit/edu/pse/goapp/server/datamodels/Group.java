/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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