/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Interface group dao
 */
public interface GroupDAO {

    /**
     * Add new group
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void addGroup() throws IOException, CustomServerException;

    /**
     * Returns a list of all groups
     * 
     * @return list List of groups
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public List<Group> getAllGroups() throws IOException, CustomServerException;

    /**
     * Updates group information
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void updateGroup() throws IOException, CustomServerException;

    /**
     * Deletes an existing group
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void deleteGroup() throws IOException, CustomServerException;

    /**
     * Returns Group by ID
     * 
     * @return group Group
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public Group getGroupByID() throws IOException, CustomServerException;

    /**
     * Sets name of the group
     * 
     * @param name
     *            name of the group
     */
    void setName(String name);

    /**
     * Returns name of the group
     * 
     * @return name name of the group
     */
    String getName();

    /**
     * Set group ID
     * 
     * @param groupId
     *            group ID
     */
    void setGroupId(int groupId);

    /**
     * Returns groupId
     * 
     * @return groupId groupId
     */
    int getGroupId();

    /**
     * Set userId
     * 
     * @param userId
     *            userId
     */
    void setUserId(int userId);

    /**
     * Returns userId
     * 
     * @return userId userId
     */
    int getUserId();

}
