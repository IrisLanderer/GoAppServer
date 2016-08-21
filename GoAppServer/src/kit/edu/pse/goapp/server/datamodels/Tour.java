/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

/**
 * Database of a tour. Extends class Meeting.
 */
public class Tour extends Meeting {
	public static double MAX_CENTER_DISTANCE = 30.00;
	private MeetingCenter center;

	/**
	 * Constructor. Sets variables of the tour.
	 * 
	 * @param id
	 *            ID of the tour
	 * @param name
	 *            name of the tour
	 * @param place
	 *            place of the tour
	 * @param timestamp
	 *            timestamp of the tour
	 * @param duration
	 *            duration of the tour
	 * @param creator
	 *            creator of the tour
	 */
	public Tour(int id, String name, GPS place, long timestamp, int duration, Participant creator) {
		super(id, name, place, timestamp, duration, creator);
		this.center = new MeetingCenter(place);

	}

	/**
	 * Returns meeting center
	 * 
	 * @return center meeting center
	 */
	public MeetingCenter getCenter() {
		return center;
	}

	/**
	 * Returns meeting center
	 * 
	 * @param center
	 *            of meeting
	 * 
	 * @return center meeting center
	 */
	public void setCenter(MeetingCenter center) {
		this.center = center;
	}

	/**
	 * Equals an object to this tour
	 * 
	 * @param obj
	 *            Object to compare
	 * @return boolean true if object is equal to this tour, else false
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
		Tour u = (Tour) obj;
		if (u.getCenter().equals(center)) {
			Meeting m = (Meeting) obj;
			if (m.getMeetingId() == meetingId && m.getPlace().equals(place) && m.getCreator().equals(creator)
					&& m.getDuration() == duration && m.getName().equals(name)
					&& m.getParticipants().equals(participants) && m.getTimestamp() == timestamp) {
				return true;
			}
		}

		return false;
	}
}
