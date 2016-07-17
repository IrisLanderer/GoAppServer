/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Interface ParticipantDAO
 */
public interface ParticipantDAO {

    /**
     * Add participant
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomerException
     */
    public void addParticipant() throws IOException, CustomServerException;

    /**
     * Deletes participant
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void deleteParticipant() throws IOException, CustomServerException;

    /**
     * Returns a list of all participants
     * 
     * @return participants list of all participants
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public List<Participant> getAllParticipants()
            throws IOException, CustomServerException;

    /**
     * Returns a participant
     * 
     * @return participant participant
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public Participant getParticipantByID()
            throws IOException, CustomServerException;

    /**
     * Updates participant information
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerexception
     */
    public void updateParticipant() throws IOException, CustomServerException;

    /**
     * Sets participant confirmation
     * 
     * @param confirmation
     *            participant confirmation
     */
    void setConfirmation(MeetingConfirmation confirmation);

    /**
     * Returns meetingConfirmation of an user
     * 
     * @return meetingConfirmation meeting confirmation
     */
    MeetingConfirmation getConfirmation();

    /**
     * set meeting id
     * 
     * @param meetingId
     *            meetingId
     */
    void setMeetingId(int meetingId);

    /**
     * Return meetingId
     * 
     * @return meetingId meetingId
     */
    int getMeetingId();

    /**
     * Set userId
     * 
     * @param userId
     *            userId
     */
    void setUserId(int userId);

    /**
     * Return userId
     * 
     * @return userId
     */
    int getUserId();

    /**
     * Set participantId
     * 
     * @param participantId
     *            participantId
     */
    void setParticipantId(int participantId);

    /**
     * Return participantId
     * 
     * @return participantId participantId
     */
    int getParticipantId();

}
