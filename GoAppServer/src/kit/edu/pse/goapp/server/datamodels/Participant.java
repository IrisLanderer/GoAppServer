/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

/**
 * Database for a participant of a meeting
 */
public class Participant {
    private int participantId;
    private int meetingId;
    private User user;
    private MeetingConfirmation confirmation;

    /**
     * Constructor. Sets variables of the participant
     * 
     * @param participantId
     *            ID of the participant
     * @param meetingId
     *            ID of the meeting
     * @param user
     *            user
     * @param confirmation
     *            participant confirmation
     */
    public Participant(int participantId, int meetingId, User user,
            MeetingConfirmation confirmation) {
        this.participantId = participantId;
        this.meetingId = meetingId;
        this.user = user;
        this.confirmation = confirmation;
    }

    /**
     * Returns participant id
     * 
     * @return participantId participantId
     */
    public int getParticipantId() {
        return participantId;
    }

    /**
     * Returns meetingId
     * 
     * @return meetingId meetingID
     */
    public int getMeetingId() {
        return meetingId;
    }

    /**
     * Set meetingId
     * 
     * @param meetingId
     *            meetingId
     */
    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    /**
     * Returns participant
     * 
     * @return user participant
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns meetingConfirmation of a participant
     * 
     * @return confirmation meetingConfirmation
     */
    public MeetingConfirmation getConfirmation() {
        return confirmation;
    }

    /**
     * Equals an object to this participant
     * 
     * @param obj
     *            Object to compare
     * @return boolean true if object is equal to this participant, else false
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
        Participant p = (Participant) obj;
        if ((p.getMeetingId() == meetingId)
                && (p.getParticipantId() == participantId)
                && (p.getUser().equals(user))
                && (p.getConfirmation() == confirmation)) {
            return true;
        }

        return false;
    }
}
