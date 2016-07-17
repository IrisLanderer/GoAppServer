/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

/**
 * Datamodel of GroupMembers
 */
public class GroupMember {

    private int groupId;
    private int userId;
    private boolean isAdmin;

    /**
     * Returns groupId
     * 
     * @return groupId GroupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets GroupId
     * 
     * @param groupId
     *            GroupId
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns UserId
     * 
     * @return userId UserId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets UserId
     * 
     * @param userId
     *            UserId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Shows if a group member ist group admin
     * 
     * @return isAdmin true if group member is admin, else false
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Sets a group member as admin
     * 
     * @param isAdmin
     *            true if the groupMember is admin, else false
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Equals an object to this groupMember
     * 
     * @param obj
     *            Object to compare
     * @return boolean true if object is equal to this groupMember, else false
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
        GroupMember g = (GroupMember) obj;
        if (g.getGroupId() == groupId && g.getUserId() == userId
                && g.isAdmin() == isAdmin) {
            return true;
        }
        return false;
    }

}
