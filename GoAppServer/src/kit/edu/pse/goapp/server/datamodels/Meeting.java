/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.datamodels;

import java.util.ArrayList;
import java.util.List;

public abstract class Meeting {

	public static double RADIUS = 200.00;

	protected int meetingId;
	protected String name;
	protected GPS place;
	protected long timespamp;
	protected int duration;
	protected Participant creator;
	protected List<Participant> participants;

	public Meeting(int meetingId, String name, GPS place, long timestamp, int duration, Participant creator) {
		this.meetingId = meetingId;
		this.name = name;
		this.place = place;
		this.timespamp = timestamp;
		this.duration = duration;
		this.creator = creator;
		this.participants = new ArrayList<Participant>();

	}

	public void addParticipant(Participant participant) {
		participants.add(participant);
	}

	public GPS getPlace() {
		return place;
	}

	public long getTimespamp() {
		return timespamp;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public Participant getCreator() {
		return creator;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

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
		if (m.getMeetingId() == meetingId && m.getPlace().equals(place) && m.getCreator().equals(creator)
				&& m.getDuration() == duration && m.getName().equals(name) && m.getParticipants().equals(participants)
				&& equals(m.getTimespamp() == timespamp)) {
			return true;
		}
		return false;
	}

}
