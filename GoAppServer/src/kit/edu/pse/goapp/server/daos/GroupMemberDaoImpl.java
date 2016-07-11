package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;

public class GroupMemberDaoImpl implements GroupMemberDAO {

	private int groupId;
	private int userId;
	private boolean isAdmin;

	private List<Integer> memberIds = new ArrayList<>();
	private List<Integer> adminIds = new ArrayList<>();

	public GroupMemberDaoImpl() {
		super();
	}

	@Override
	public void addMember() throws IOException {
		if (groupId <= 0) {
			throw new IllegalArgumentException("A group must have an GroupID!");
		}
		if (userId <= 0) {
			throw new IllegalArgumentException("A group must have an UserID!");
		}
		try (DatabaseConnection conn = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"INSERT INTO group_members (groups_id, users_id, is_admin) VALUES (''{0}'', ''{1}'', ''{2}'')",
					groupId, userId, isAdmin == true ? 1 : 0);
			conn.insert(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteMember() throws IOException {
		if (groupId <= 0) {
			throw new IllegalArgumentException("A group must have an GroupID!");
		}
		if (userId <= 0) {
			throw new IllegalArgumentException("A group must have an UserID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"DELETE FROM group_members" + " WHERE groups_id = ''{0}'' AND users_id = ''{1}''", groupId, userId);
			connection.delete(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void updateMember() throws IOException {
		if (groupId <= 0) {
			throw new IllegalArgumentException("A group must have an GroupID!");
		}
		if (userId <= 0) {
			throw new IllegalArgumentException("A group must have an UserID!");
		}
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"UPDATE group_members SET is_admin = ''{0}'' " + "WHERE groups_id = ''{1}'' AND users_id = ''{2}''",
					isAdmin == true ? 1 : 0, groupId, userId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public List<User> getAllMembers() throws IOException {
		List<User> members = new ArrayList<>();
		try (DatabaseConnection connection = new DatabaseConnection()) {
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
		return members;
	}

	@Override
	public List<User> getAllAdmins() throws IOException {
		List<User> admins = new ArrayList<>();
		try (DatabaseConnection connection = new DatabaseConnection()) {
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
		return admins;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public void setAdmin() {
		// TODO Auto-generated method stub

	}

	private final class MembersSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				memberIds.add(resultSet.getInt(1));

			}
		}

	}

	private final class AdminsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				if (resultSet.getBoolean(2)) {
					adminIds.add(resultSet.getInt(1));
				}

			}
		}

	}

}
