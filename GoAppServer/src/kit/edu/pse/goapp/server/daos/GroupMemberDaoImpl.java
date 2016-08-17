/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Implements interface GroupMemberDao
 */
public class GroupMemberDaoImpl implements GroupMemberDAO {

	private int groupId;
	private int userId;
	private boolean isAdmin;

	private List<Integer> memberIds = new ArrayList<>();
	private List<Integer> adminIds = new ArrayList<>();

	/**
	 * Constructor.
	 */
	public GroupMemberDaoImpl() {
		super();
	}

	/**
	 * Add new groupMember
	 * 
	 * @throws IOException
	 *             IOException
	 */
	@Override
	public void addMember() throws IOException {
		try {
			this.addMember(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * Add new group member to database
	 * 
	 * @param conn
	 *            DatabaseConnection
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	protected void addMember(DatabaseConnection conn) throws IOException, CustomServerException {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (userId <= 0) {
			throw new CustomServerException("A group must have an UserID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"INSERT INTO group_members (groups_id, users_id, is_admin) VALUES (''{0}'', ''{1}'', ''{2}'')",
					groupId, userId, isAdmin == true ? 1 : 0);
			conn.insert(query);
			conn.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	/**
	 * Deletes a group member
	 */
	@Override
	public void deleteMember() throws IOException {
		try {
			this.deleteMember(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * Deletes a group member from database
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	protected void deleteMember(DatabaseConnection connection) throws IOException, CustomServerException {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (userId <= 0) {
			throw new CustomServerException("A group must have an UserID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"DELETE FROM group_members" + " WHERE groups_id = ''{0}'' AND users_id = ''{1}''", groupId, userId);
			connection.delete(query);
			connection.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	/**
	 * Updates group member
	 */
	@Override
	public void updateMember() throws IOException {
		try {
			this.updateMember(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * Updates group member in database
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	protected void updateMember(DatabaseConnection connection) throws IOException, CustomServerException {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (userId <= 0) {
			throw new CustomServerException("A group must have an UserID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"UPDATE group_members SET is_admin = ''{0}'' " + "WHERE groups_id = ''{1}'' AND users_id = ''{2}''",
					isAdmin == true ? 1 : 0, groupId, userId);
			connection.update(query);
			connection.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	/**
	 * Returns a list of all group members
	 */
	@Override
	public List<User> getAllMembers() throws IOException {
		List<User> members = new ArrayList<>();
		try {
			members = getAllMembers(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return members;
	}

	/**
	 * Returns a list of all group members
	 * 
	 * @param connection
	 *            Databaseconnection
	 * @return members list of all group members
	 * @throws Exception
	 *             Exception
	 */
	protected List<User> getAllMembers(DatabaseConnection connection) throws Exception {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		List<User> members = new ArrayList<>();
		try {
			String query = MessageFormat.format(
					"SELECT group_members.users_id FROM group_members WHERE group_members.groups_id =  ''{0}''",
					groupId);
			connection.select(query, new MembersSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException();
		}
		for (int tmpMemberId : memberIds) {
			UserDAO dao = new UserDaoImpl();
			dao.setUserId(tmpMemberId);
			User user = dao.getUserByID();
			members.add(user);
		}
		if (members.isEmpty()) {
			throw new CustomServerException("A group must have a member!", HttpServletResponse.SC_BAD_REQUEST);
		}
		connection.close();
		return members;
	}

	/**
	 * Returns a list of all group admins
	 */
	@Override
	public List<User> getAllAdmins() throws IOException {
		List<User> admins = null;
		try {
			admins = getAllAdmins(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return admins;
	}

	/**
	 * Returns a list of all group admins
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @return admins list of all admins
	 * @throws Exception
	 *             Exception
	 */
	protected List<User> getAllAdmins(DatabaseConnection connection) throws Exception {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		List<User> admins = new ArrayList<>();
		try {
			String query = MessageFormat.format(
					"SELECT group_members.users_id, group_members.is_admin FROM group_members WHERE group_members.groups_id =  ''{0}''",
					groupId);
			connection.select(query, new AdminsSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException();
		}
		for (int tmpAdminId : adminIds) {
			UserDAO dao = new UserDaoImpl();
			dao.setUserId(tmpAdminId);
			User user = dao.getUserByID();
			admins.add(user);
		}
		if (admins.isEmpty()) {
			throw new CustomServerException("A group must have an admin!", HttpServletResponse.SC_BAD_REQUEST);
		}
		connection.close();
		return admins;
	}

	/**
	 * Returns groupId
	 */
	@Override
	public int getGroupId() {
		return groupId;
	}

	/**
	 * Sets groupId
	 */
	@Override
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * Returns userId
	 */
	@Override
	public int getUserId() {
		return userId;
	}

	/**
	 * Set userId
	 */
	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Returns if the group member is group admin
	 */
	@Override
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Sets a group member as group admin
	 */
	@Override
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * sets member IDs
	 * 
	 * @param memberIds
	 *            all members' IDs
	 */
	public void setMemberIds(List<Integer> memberIds) {
		this.memberIds = memberIds;
	}

	/**
	 * sets admin IDs
	 * 
	 * @param adminIds
	 *            all admins' IDs
	 */
	public void setAdminIds(List<Integer> adminIds) {
		this.adminIds = adminIds;
	}

	/**
	 * handles the data from the database's result set for the method
	 * getAllMembers
	 */
	private final class MembersSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {

			boolean resultEmpty = true;

			while (resultSet.next()) {
				resultEmpty = false;
				memberIds.add(resultSet.getInt(1));

			}

			if (resultEmpty) {
				throw new CustomServerException("There aren't any members of this group!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}

	}

	/**
	 * handles the data from the database's result set for the method
	 * getAllAdmins
	 */
	private final class AdminsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {

			boolean resultEmpty = true;

			while (resultSet.next()) {
				resultEmpty = false;
				if (resultSet.getBoolean(2)) {
					adminIds.add(resultSet.getInt(1));
				}

			}

			if (resultEmpty) {
				throw new CustomServerException("There aren't any admins of this group!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}

	}

}
