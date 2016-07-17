/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Database of meeting center
 */
public class MeetingCenter {
    private List<Participant> participants;
    private GPS place;

    /**
     * Constructor. Sets variables of meeting center
     * 
     * @param place
     */
    public MeetingCenter(GPS place) {
        this.place = place;
        this.participants = new ArrayList<Participant>();
    }

    /**
     * Adds participants to center
     * 
     * @param participant
     *            participant
     */
    public void addParticipantsToCenter(Participant participant) {
        participants.add(participant);
    }

    /**
     * Returns MeetingCenter
     * 
     * @return null
     */
    public MeetingCenter calculateCenter() {
        // TODò
        return null;
    }

    /**
     * Returns List of Participants
     * 
     * @return participants List of participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Returns GPS position
     * 
     * @return place GPS position
     */
    public GPS getPlace() {
        return place;
    }

    /**
     * Sets participants in meeting center
     * 
     * @param participants
     *            list of participants in meeting center
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * Equals an object to this meetingCenter
     * 
     * @param obj
     *            Object to compare
     * @return boolean true if object is equal to this meetingCenter, else false
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
        MeetingCenter c = (MeetingCenter) obj;
        if (c.getPlace().equals(place)
                && c.getParticipants().equals(participants)) {
            return true;
        }
        return false;
    }

}
