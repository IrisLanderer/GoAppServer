/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

/**
 * Datamodel of an event Extends class Meeting
 */
public class Event extends Meeting {

    /**
     * Constructor. Creates a new event and sets the variables
     * 
     * @param id
     *            ID of the event
     * @param name
     *            name of the event
     * @param place
     *            place of the event
     * @param timestamp
     *            timestamp of the event
     * @param duration
     *            duration of the event
     * @param creator
     *            groupmember which creates the event
     */
    public Event(int id, String name, GPS place, long timestamp, int duration,
            Participant creator) {
        super(id, name, place, timestamp, duration, creator);

    }

    /**
     * Equals an object to this meeting
     * 
     * @param obj
     *            Object to compare
     * @return boolean true if object is equal to this meeting, else false
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
        Meeting m = (Meeting) obj;
        if (m.getMeetingId() == meetingId && m.getPlace().equals(place)
                && m.getCreator().equals(creator) && m.getDuration() == duration
                && m.getName().equals(name)
                && m.getParticipants().equals(participants)
                && m.getTimestamp() == timestamp) {
            return true;
        }

        return false;
    }
}
