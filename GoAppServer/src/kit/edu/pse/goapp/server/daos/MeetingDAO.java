/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Interface Meeting DAO
 */
public interface MeetingDAO {

    /**
     * Add new meeting
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void addMeeting() throws IOException, CustomServerException;

    /**
     * Returns list of all meetings
     * 
     * @return meetings list of all meetings
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public List<Meeting> getAllMeetings()
            throws IOException, CustomServerException;

    /**
     * Updates meeting information
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void updateMeeting() throws IOException, CustomServerException;

    /**
     * Deletes a meeting
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void deleteMeeting() throws IOException, CustomServerException;

    /**
     * Returns a meeting by its ID
     * 
     * @return meeting meeting by ID
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public Meeting getMeetingByID() throws IOException, CustomServerException;

    /**
     * Sets creatorId of the meeting
     * 
     * @param creatorId
     *            creatorId
     */
    void setCreatorId(int creatorId);

    /**
     * Returns creatorId
     * 
     * @return creatorId creatorId
     */
    int getCreatorId();

    /**
     * Sets type of meeting ('tour' or 'event')
     * 
     * @param type
     *            type of meeting
     */
    void setType(String type);

    /**
     * Returns type of meeting ('tour' or 'event'
     * 
     * @return type type of meeting as string
     */
    String getType();

    /**
     * Set duration of the meeting
     * 
     * @param duration
     *            duration of the meeting
     */
    void setDuration(int duration);

    /**
     * Returns duration of the meeting
     * 
     * @return duration duration
     */
    int getDuration();

    /**
     * Set timestamp of the meeting
     * 
     * @param timestamp
     *            timestamp
     */
    void setTimestamp(long timestamp);

    /**
     * Return timestamp
     * 
     * @return timestamp timestamp
     */
    long getTimestamp();

    /**
     * Set GPS z parameter
     * 
     * @param placeZ
     *            GPS z parameter
     */
    void setPlaceZ(double placeZ);

    /**
     * Returns GPS z parameter
     * 
     * @return z GPS z parameter
     */
    double getPlaceZ();

    /**
     * Set GPS y parameter
     * 
     * @param placeY
     *            GPS y parameter
     */
    void setPlaceY(double placeY);

    /**
     * Returns GPS y parameter
     * 
     * @return y GPS y parameter
     */
    double getPlaceY();

    /**
     * Set GPS x parameter
     * 
     * @param placeX
     *            GPS x parameter
     */
    void setPlaceX(double placeX);

    /**
     * Returns GPS x parameter
     * 
     * @return x GPS x parameter
     */
    double getPlaceX();

    /**
     * Set name of the meeting
     * 
     * @param name
     *            name
     */
    void setName(String name);

    /**
     * Returns name of the meeting
     * 
     * @return name name of the meeting
     */
    String getName();

    /**
     * Sets meetingId
     * 
     * @param meetingId
     *            meetingId
     */
    void setMeetingId(int meetingId);

    /**
     * Returns meetingId
     * 
     * @return meetingId meetingId
     */
    int getMeetingId();

    /**
     * Sets userId
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
