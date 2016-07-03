package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;

public class GroupDaoImpl implements GroupDAO {

	private int groupId;

	private String name;
	
	private final List<User> admins = new ArrayList<>();
	
	private final List<User> members = new ArrayList<>();

	public GroupDaoImpl() {
		super();
	}

	@Override
	public void addGroup() throws IOException {
		if (name == null) {
			throw new IllegalArgumentException("A new group must have a name!");
		}

		try (DatabaseConnection conn = new DatabaseConnection()) {
			String sqlStmt = MessageFormat.format("INSERT INTO groups (name) VALUES (''{0}'')", name);
			groupId = conn.insert(sqlStmt);
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<Group> getAllGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGroup() throws IOException {
		if (groupId == -1) {
			throw new IllegalArgumentException("A group must have an ID!");
		}
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format("UPDATE groups SET name = ''{0}'' WHERE group_id = ''{1}''", name, groupId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteGroup() throws IOException {
		if (groupId == -1) {
			throw new IllegalArgumentException("A group must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
		String query = MessageFormat.format("DELETE FROM groups WHERE group_id = ''{0}''", groupId);
		connection.delete(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public Group getGroupByID() throws IOException {
		if (groupId == -1) {
			throw new IllegalArgumentException("A group must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"SELECT g.group_id, g.name, m.users_id, m.is_admin, u.name "
							+ "FROM groups g left outer join (group_members m left outer join users u on m.users_id = u.users_id) on g.group_id = m.groups_id "
							+ "WHERE g.group_id = ''{0}''", groupId);
			connection.select(query, new GroupSqlSelectionHandler());
			Group group = new Group(groupId, name.toString());
			group.setMembers(members);
			group.setAdmins(admins);
			return group;
		} catch (Throwable e) {
			throw new IOException(e);
		}
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
	
	private final class GroupSqlSelectionHandler implements SqlSelectHandler {
		@Override 
		public void handleResultSet(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				if(name == null) {
					name = resultSet.getString(2);
				}
				User user = new User(resultSet.getInt(3), resultSet.getString(5));
				if (resultSet.getBoolean(4)) {
					admins.add(user);
					admins.add(user);
					} else {
					members.add(user);					
				}
			}
		}
	}
}
