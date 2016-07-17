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
    protected void addMember(DatabaseConnection conn)
            throws IOException, CustomServerException {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        if (userId <= 0) {
            throw new CustomServerException("A group must have an UserID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        // checks if this user is already a member of this group. not yet tested
        // and a requirement with priority b
        // List<User> groupMembers = createMembersWithDao();
        // for (User member : groupMembers) {
        // if (member.getId() == userId) {
        // throw new CustomServerException("The user is already a member of this
        // group!",
        // HttpServletResponse.SC_BAD_REQUEST);
        // }
        // }
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
    protected void deleteMember(DatabaseConnection connection)
            throws IOException, CustomServerException {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        if (userId <= 0) {
            throw new CustomServerException("A group must have an UserID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        // checks if this user is a member of this group at all. not yet tested
        // and a requirement with priority b
        // List<User> groupMembers = createMembersWithDao();
        // checkIfMember(groupMembers);
        try {
            String query = MessageFormat.format(
                    "DELETE FROM group_members"
                            + " WHERE groups_id = ''{0}'' AND users_id = ''{1}''",
                    groupId, userId);
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
    protected void updateMember(DatabaseConnection connection)
            throws IOException, CustomServerException {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        if (userId <= 0) {
            throw new CustomServerException("A group must have an UserID!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        // checks if this user is a member of this group at all. not yet tested
        // and a requirement with priority b
        // List<User> groupMembers = createMembersWithDao();
        // checkIfMember(groupMembers);
        try {
            String query = MessageFormat.format(
                    "UPDATE group_members SET is_admin = ''{0}'' "
                            + "WHERE groups_id = ''{1}'' AND users_id = ''{2}''",
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
        List<User> members = null;
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
    protected List<User> getAllMembers(DatabaseConnection connection)
            throws Exception {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
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
    protected List<User> getAllAdmins(DatabaseConnection connection)
            throws Exception {
        if (groupId <= 0) {
            throw new CustomServerException("A group must have an GroupID!",
                    HttpServletResponse.SC_BAD_REQUEST);
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
     * Returns a list of users created with the dao
     * 
     * @return groupMembers list of groupMembers
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    private List<User> createMembersWithDao()
            throws IOException, CustomServerException {
        GroupDAO dao = new GroupDaoImpl();
        dao.setGroupId(groupId);
        Group group = dao.getGroupByID();
        List<User> groupMembers = group.getGroupMembers();
        return groupMembers;
    }

    /**
     * Checks if an user is group member
     * 
     * @param groupMembers
     *            list of group members
     * @throws CustomServerException
     *             CustomServerException
     */
    private void checkIfMember(List<User> groupMembers)
            throws CustomServerException {
        boolean isMember = false;
        for (User member : groupMembers) {
            if (member.getId() == userId) {
                isMember = true;
            }
        }
        if (!isMember) {
            throw new CustomServerException(
                    "The user is not a member of this group!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private final class MembersSqlSelectionHandler implements SqlSelectHandler {
        @Override
        public void handleResultSet(ResultSet resultSet)
                throws SQLException, CustomServerException {

            boolean resultEmpty = true;

            while (resultSet.next()) {
                resultEmpty = false;
                memberIds.add(resultSet.getInt(1));

            }

            if (resultEmpty) {
                throw new CustomServerException(
                        "The selected resultset from the database is empty",
                        HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    private final class AdminsSqlSelectionHandler implements SqlSelectHandler {
        @Override
        public void handleResultSet(ResultSet resultSet)
                throws SQLException, CustomServerException {

            boolean resultEmpty = true;

            while (resultSet.next()) {
                resultEmpty = false;
                if (resultSet.getBoolean(2)) {
                    adminIds.add(resultSet.getInt(1));
                }

            }

            if (resultEmpty) {
                throw new CustomServerException(
                        "The selected resultset from the database is empty",
                        HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

}
