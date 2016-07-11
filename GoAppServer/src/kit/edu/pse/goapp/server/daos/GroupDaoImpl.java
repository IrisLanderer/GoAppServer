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

import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public class GroupDaoImpl implements GroupDAO {

	private int groupId;

	private int userId;

	private String name;

	private final List<User> admins = new ArrayList<>();

	private final List<User> members = new ArrayList<>();
	private List<Integer> groupIds = new ArrayList<>();

	public GroupDaoImpl() {
		super();
	}

	@Override
	public void addGroup() throws IOException, CustomServerException {
		if (name == null) {
			throw new CustomServerException("A new group must have a name!", HttpServletResponse.SC_BAD_REQUEST);
		}

		try (DatabaseConnection conn = new DatabaseConnection()) {
			String sqlStmt = MessageFormat.format("INSERT INTO groups (name) VALUES (''{0}'')", name);
			groupId = conn.insert(sqlStmt);
			if (groupId <= 0) {
				throw new CustomServerException("The GroupID wasn't assigned to a value!",
						HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<Group> getAllGroups() throws IOException, CustomServerException {
		List<Group> groups = new ArrayList<>();
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"SELECT group_members.groups_id FROM group_members WHERE group_members.users_id =  ''{0}''",
					userId);
			connection.select(query, new GroupsSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException();
		}
		for (int tmpGroupId : groupIds) {
			GroupDAO dao = new GroupDaoImpl();
			dao.setGroupId(tmpGroupId);
			Group group = dao.getGroupByID();
			groups.add(group);
		}
		return groups;
	}

	@Override
	public void updateGroup() throws IOException, CustomServerException {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (name == null) {
			throw new CustomServerException(
					"You cannot change the name of a group to null, because a group must have a name!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		checkAuthorization();
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format("UPDATE groups SET name = ''{0}'' WHERE group_id = ''{1}''", name,
					groupId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteGroup() throws IOException, CustomServerException {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		checkAuthorization();
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String queryGroup = MessageFormat.format("DELETE FROM groups WHERE group_id = ''{0}''", groupId);
			String queryGroupMember = MessageFormat.format("DELETE FROM group_members WHERE groups_id = ''{0}''",
					groupId);
			connection.delete(queryGroup);
			connection.delete(queryGroupMember);

		} catch (Throwable e) {

			throw new IOException(e);
		}

	}

	@Override
	public Group getGroupByID() throws IOException, CustomServerException {
		if (groupId <= 0) {
			throw new CustomServerException("A group must have an GroupID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		checkAuthorization();
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat
					.format("SELECT g.group_id, g.name, m.users_id, m.is_admin, u.name FROM groups g left outer join "
							+ "(group_members m left outer join users u on m.users_id = u.users_id)"
							+ " on g.group_id = m.groups_id WHERE g.group_id = ''{0}''", groupId);
			connection.select(query, new GroupSqlSelectionHandler());
		} catch (Throwable e) {
			if (e.getCause().getClass() == CustomServerException.class) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			throw new IOException(e);
		}
		Group group = new Group(groupId, name.toString());
		group.setGroupMembers(members);
		group.setAdmins(admins);
		return group;
	}

	private void checkAuthorization() throws IOException, CustomServerException {

		GroupMemberDAO groupMemberDao = new GroupMemberDaoImpl();
		groupMemberDao.setGroupId(groupId);
		groupMemberDao.setUserId(userId);
		UserDAO userDao = new UserDaoImpl();
		userDao.setUserId(userId);
		User user = userDao.getUserByID();
		List<User> tmpMembers = groupMemberDao.getAllMembers();

		if (!checkMemberAndAdmin(user, tmpMembers)) {
			throw new CustomServerException("The user has to be member of this group to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
		List<User> tmpAdmins = groupMemberDao.getAllAdmins();
		if (!checkMemberAndAdmin(user, tmpAdmins)) {
			throw new CustomServerException("The user has to be admin of this group to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean checkMemberAndAdmin(User user, List<User> users) {
		boolean isAuthorized = false;
		for (User tmpUser : users) {
			if (user.getId() == tmpUser.getId()) {
				isAuthorized = true;
			}
		}
		return isAuthorized;
	}

	@Override
	public int getGroupId() {
		return groupId;
	}

	@Override
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	private final class GroupSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {

			boolean resultEmpty = true;
			while (resultSet.next()) {
				resultEmpty = false;
				groupId = resultSet.getInt(1);
				name = resultSet.getString(2);
				User user = new User(resultSet.getInt(3), resultSet.getString(5));
				if (resultSet.getBoolean(4)) {
					admins.add(user);
					members.add(user);
				} else {
					members.add(user);
				}
			}
			if (resultEmpty) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}

		}
	}

	private final class GroupsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {

			boolean resultEmpty = true;

			while (resultSet.next()) {
				resultEmpty = false;
				groupIds.add(resultSet.getInt(1));

			}

			if (resultEmpty) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}

	}
}
