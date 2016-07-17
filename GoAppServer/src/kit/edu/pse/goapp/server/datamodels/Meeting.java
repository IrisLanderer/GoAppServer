/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public abstract class Meeting {

	public static double RADIUS = 200.00;

	protected int meetingId;
	protected String name;
	protected GPS place;
	protected long timestamp;
	protected int duration;
	protected Participant creator;
	protected List<Participant> participants;

	public Meeting(int meetingId, String name, GPS place, long timestamp, int duration, Participant creator) {
		this.meetingId = meetingId;
		this.name = name;
		this.place = place;
		this.timestamp = timestamp;
		this.duration = duration;
		this.creator = creator;
		this.participants = new ArrayList<Participant>();

	}

	public void isParticipant(User user) throws IOException, CustomServerException {
		ParticipantDAO participantDao = new ParticipantDaoImpl();
		participantDao.setMeetingId(meetingId);
		List<Participant> participants = participantDao.getAllParticipants();
		boolean isParticipant = false;
		for (Participant participant : participants) {
			if (participant.getUser().getId() == user.getId()) {
				isParticipant = true;
			}
		}
		if (!isParticipant) {
			throw new CustomServerException("The user has to be participant of this meeting to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	public void isCreator(User user) throws IOException, CustomServerException {
		MeetingDAO meetingDao = new MeetingDaoImpl();
		meetingDao.setMeetingId(meetingId);
		Meeting meeting = meetingDao.getMeetingByID();
		int creatorId = meeting.getCreator().getParticipantId();
		ParticipantDAO participantDAO = new ParticipantDaoImpl();
		participantDAO.setParticipantId(creatorId);
		Participant creator = participantDAO.getParticipantByID();
		if (!(creator.getUser().getId() == user.getId())) {
			throw new CustomServerException("The user has to be creator of this meeting to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	public void addParticipant(Participant participant) {
		participants.add(participant);
	}

	public GPS getPlace() {
		return place;
	}

	public long getTimestamp() {
		return timestamp;
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
				&& equals(m.getTimestamp() == timestamp)) {
			return true;
		}
		return false;
	}

}
