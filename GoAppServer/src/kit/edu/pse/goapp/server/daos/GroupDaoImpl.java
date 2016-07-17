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

/**
 * Implements Group dao
 */
public class GroupDaoImpl implements GroupDAO {

    private int groupId;

    private int userId;

    private String name;

    private final List<User> admins = new ArrayList<>();

    private final List<User> members = new ArrayList<>();
    private List<Integer> groupIds = new ArrayList<>();

    /**
     * Constructor. Calls super class
     */
    public GroupDaoImpl() {
        super();
    }

    /**
     * Add new group
     * 
     * @throws IOException
     *             IOException
     */
    @Override
    public void addGroup() throws IOException {
        try {
            this.addGroup(new DatabaseConnection());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Add new group to database
     * 
     * @param conn
     *            DatabaseConnection
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    protected void addGroup(DatabaseConnection conn)
            throws IOException, CustomServerException {
        if (name == null || name.equals("")) {
            throw new CustomServerException("A new group must have a name!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            String queryAddGroup = MessageFormat
                    .format("INSERT INTO groups (name) VALUES (''{0}'')", name);
            groupId = conn.insert(queryAddGroup);
            String queryAddCreatorToGroup = MessageFormat.format(
                    "INSERT INTO group_members VALUES (''{0}'', ''{1}'', ''{2}'')",
                    groupId, userId, 1);
            conn.insert(queryAddCreatorToGroup);
            if (groupId <= 0) {
                throw new CustomServerException(
                        "The GroupID wasn't assigned to a value!",
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            conn.close();
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }

    /**
     * Returns a list of all groups
     * 
     * @return groups list of groups
     * @throw IOException IOException
     */
    @Override
    public List<Group> getAllGroups() throws IOException {
        List<Group> groups = null;
        try {
            groups = getAllGroups(new DatabaseConnection());
        } catch (Exception e) {
            throw new IOException(e);
        }
        return groups;
    }

    /**
     * Returns a list of all groups from database
     * 
     * @param connection
     *            DatabaseConnection
     * @return groups list of groups
     * @throws Exception
     *             Exception
     */
    protected List<Group> getAllGroups(DatabaseConnection connection)
            throws Exception {
        List<Group> groups = new ArrayList<>();
        try {
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
        connection.close();
        return groups;
    }

    /**
     * Updates group information
     * 
     * @throws IOEXception
     */
    @Override
    public void updateGroup() throws IOException {
        try {
            updateGroup(new DatabaseConnection());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Updates group in database
     * 
     * @param connection
     *            DatabaseConnection
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    protected void updateGroup(DatabaseConnection connection)
            throws IOException, CustomServerException {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        if (name == null || name.equals("")) {
            throw new CustomServerException(
                    "You cannot change the name of a group to null, because a group must have a name!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            String query = MessageFormat.format(
                    "UPDATE groups SET name = ''{0}'' WHERE group_id = ''{1}''",
                    name, groupId);
            connection.update(query);
            connection.close();
        } catch (Throwable e) {
            throw new IOException(e);
        }

    }

    /**
     * Deletes a group
     * 
     * @throw IOException IOException
     */
    @Override
    public void deleteGroup() throws IOException {
        try {
            deleteGroup(new DatabaseConnection());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Deletes a group from database
     * 
     * @param connection
     *            DatabaseConnection
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    protected void deleteGroup(DatabaseConnection connection)
            throws IOException, CustomServerException {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            String queryGroup = MessageFormat.format(
                    "DELETE FROM groups WHERE group_id = ''{0}''", groupId);
            String queryGroupMember = MessageFormat.format(
                    "DELETE FROM group_members WHERE groups_id = ''{0}''",
                    groupId);
            connection.delete(queryGroup);
            connection.delete(queryGroupMember);
            connection.close();
        } catch (Throwable e) {

            throw new IOException(e);
        }

    }

    /**
     * Returns group by ID
     * 
     * @return group Group with ID
     * @throw IOException IOException
     */
    @Override
    public Group getGroupByID() throws IOException {
        Group group = null;
        try {
            group = getGroupByID(new DatabaseConnection());
        } catch (Exception e) {
            throw new IOException(e);
        }
        return group;
    }

    /**
     * Returns group by ID from database
     * 
     * @param connection
     *            DatabaseConnection
     * @return group group
     * @throws Exception
     *             Exception
     */
    protected Group getGroupByID(DatabaseConnection connection)
            throws Exception {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            String query = MessageFormat.format(
                    "SELECT g.group_id, g.name, m.users_id, m.is_admin, u.name FROM groups g left outer join "
                            + "(group_members m left outer join users u on m.users_id = u.users_id)"
                            + " on g.group_id = m.groups_id WHERE g.group_id = ''{0}''",
                    groupId);
            connection.select(query, new GroupSqlSelectionHandler());
        } catch (Throwable e) {
            if (e.getCause().getClass() == CustomServerException.class) {
                throw new CustomServerException(
                        "The selected resultset from the database is empty",
                        HttpServletResponse.SC_BAD_REQUEST);
            }
            throw new IOException(e);
        }
        Group group = new Group(groupId, name.toString());
        group.setGroupMembers(members);
        group.setAdmins(admins);
        connection.close();
        return group;
    }

    /**
     * Returns groupId
     * 
     * @return groupId groupId
     */
    @Override
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets groupId
     * 
     * @param groupId
     *            groupId
     */
    @Override
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Get name of the group
     * 
     * @return name name of the group
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set name of the group
     * 
     * @param name
     *            name of the group
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns userId
     * 
     * @return userId userId
     */
    @Override
    public int getUserId() {
        return userId;
    }

    /**
     * Set userId
     * 
     * @param userId
     *            userId
     */
    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns list with all group ids
     * 
     * @return groupIds List of all group ids
     */
    public List<Integer> getGroupIds() {
        return groupIds;
    }

    /**
     * 
     */
    private final class GroupSqlSelectionHandler implements SqlSelectHandler {
        @Override
        public void handleResultSet(ResultSet resultSet)
                throws SQLException, CustomServerException {

            boolean resultEmpty = true;
            while (resultSet.next()) {
                resultEmpty = false;
                groupId = resultSet.getInt(1);
                name = resultSet.getString(2);
                User user = new User(resultSet.getInt(3),
                        resultSet.getString(5));
                if (resultSet.getBoolean(4)) {
                    admins.add(user);
                    members.add(user);
                } else {
                    members.add(user);
                }
            }
            if (resultEmpty) {
                throw new CustomServerException(
                        "The selected resultset from the database is empty",
                        HttpServletResponse.SC_BAD_REQUEST);
            }

        }
    }

    private final class GroupsSqlSelectionHandler implements SqlSelectHandler {
        @Override
        public void handleResultSet(ResultSet resultSet)
                throws SQLException, CustomServerException {

            boolean resultEmpty = true;

            while (resultSet.next()) {
                resultEmpty = false;
                groupIds.add(resultSet.getInt(1));

            }

            if (resultEmpty) {
                throw new CustomServerException(
                        "The selected resultset from the database is empty",
                        HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }
}
