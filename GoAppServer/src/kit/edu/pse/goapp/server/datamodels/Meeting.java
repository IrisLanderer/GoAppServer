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

/**
 * Abstract class Meeting
 */
public abstract class Meeting {

	public static double RADIUS = 200.00;

	protected int meetingId;
	protected String name;
	protected GPS place;
	protected long timestamp;
	protected int duration;
	protected Participant creator;
	protected List<Participant> participants;

	/**
	 * Constructor. Sets all variables of a new meeting.
	 * 
	 * @param meetingId
	 *            ID of the Meeting
	 * @param name
	 *            Name of the Meeting
	 * @param place
	 *            Place of the Meeting
	 * @param timestamp
	 *            Timestamp of the Meeting
	 * @param duration
	 *            Duration of the Meeting
	 * @param creator
	 *            User who created the meeting
	 */
	public Meeting(int meetingId, String name, GPS place, long timestamp, int duration, Participant creator) {
		this.meetingId = meetingId;
		this.name = name;
		this.place = place;
		this.timestamp = timestamp;
		this.duration = duration;
		this.creator = creator;
		this.participants = new ArrayList<Participant>();

	}

	/**
	 * checks if user is participant of a meeting
	 * 
	 * @param user
	 *            user
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void isParticipant(User user) throws IOException, CustomServerException {
		ParticipantDAO participantDao = new ParticipantDaoImpl();
		participantDao.setMeetingId(meetingId);
		List<Participant> participants = participantDao.getAllParticipants();
		isParticipant(user, meetingId, participants);
	}

	protected void isParticipant(User user, int meetingId, List<Participant> participants)
			throws CustomServerException {
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

	/**
	 * checks if user is creator of a meeting
	 * 
	 * @param user
	 *            user
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void isCreator(User user) throws IOException, CustomServerException {
		MeetingDAO meetingDao = new MeetingDaoImpl();
		meetingDao.setMeetingId(meetingId);
		Meeting meeting = meetingDao.getMeetingByID();
		int creatorId = meeting.getCreator().getParticipantId();
		ParticipantDAO participantDAO = new ParticipantDaoImpl();
		participantDAO.setParticipantId(creatorId);
		Participant creator = participantDAO.getParticipantByID();
		isCreator(user, creator);

	}

	protected void isCreator(User user, Participant creator) throws CustomServerException {
		if (!(creator.getUser().getId() == user.getId())) {
			throw new CustomServerException("The user has to be creator of this meeting to access it!",
					HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	/**
	 * Add Participant of a meeting
	 * 
	 * @param participant
	 *            participant
	 */
	public void addParticipant(Participant participant) {
		participants.add(participant);
	}

	/**
	 * Returns place of the meeting
	 * 
	 * @return GPS position
	 */
	public GPS getPlace() {
		return place;
	}

	/**
	 * Returns timestamp of the meeting
	 * 
	 * @return timestamp Timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns list of participants of the meeting
	 * 
	 * @return participants List of participants
	 */
	public List<Participant> getParticipants() {
		return participants;
	}

	/**
	 * Returns meetingId
	 * 
	 * @return meetingId meetingId
	 */
	public int getMeetingId() {
		return meetingId;
	}

	/**
	 * Returns name of the meeting
	 * 
	 * @return name name of the meeting
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns duration of the meeting
	 * 
	 * @return duration meeting duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Returns Creator of the meeting
	 * 
	 * @return creator participant who created the meeting
	 */
	public Participant getCreator() {
		return creator;
	}

	/**
	 * Set Participants of the meeting
	 * 
	 * @param participants
	 *            List of participants
	 */
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
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
		if (m.getMeetingId() == meetingId && m.getPlace().equals(place) && m.getCreator().equals(creator)
				&& m.getDuration() == duration && m.getName().equals(name) && m.getParticipants().equals(participants)
				&& equals(m.getTimestamp() == timestamp)) {
			return true;
		}
		return false;
	}

}
