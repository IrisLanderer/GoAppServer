/**
 * 
 */
package kit.edu.pse.goapp.server.creating_obj_with_dao;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class ParticipantWithDao {

	/**
	 * Creates a participant with DAO
	 * 
	 * @param participantId
	 *            participantId
	 * @return participantForMeetingId participant for meeting ID
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public Participant createParticipantWithDao(int participantId) throws IOException, CustomServerException {
		ParticipantDAO participantDAO = new ParticipantDaoImpl();
		participantDAO.setParticipantId(participantId);
		Participant participantForMeetingId = participantDAO.getParticipantByID();
		return participantForMeetingId;
	}

	public List<Participant> createParticipantsWithDao(int meetingId) throws IOException, CustomServerException {
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(meetingId);
		Meeting meeting = dao.getMeetingByID();
		List<Participant> participants = meeting.getParticipants();
		return participants;
	}

}
