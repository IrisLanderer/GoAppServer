/**
 * 
 */
package kit.edu.pse.goapp.server.validation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.creating_obj_with_dao.MeetingWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.ParticipantWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class MeetingValidation {

	private UserWithDao userWithDao = new UserWithDao();
	private ParticipantWithDao participantWithDao = new ParticipantWithDao();
	private MeetingWithDao meetingWithDao = new MeetingWithDao();

	/**
	 * Checks if user is meeting participant and creator
	 * 
	 * @param userId
	 *            userId
	 * @param meetingId
	 *            meetingId
	 * @param participantId
	 *            participantId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             is user is not participant and creator of this meeting
	 */
	public void checkIfUserIsParticipantAndCreator(int userId, int meetingId)
			throws IOException, CustomServerException {
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(meetingId);
		Meeting meeting = dao.getMeetingByID();
		List<Participant> participants = participantWithDao.createParticipantsWithDao(meetingId);
		for (Participant participant : participants) {
			if (participant.getUser().getId() == userId && meeting.getCreator().getUser().getId() == userId) {
				return;
			}
		}
		throw new CustomServerException("You are not a participant and creator of this meeting!",
				HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Checks if user is meeting participant
	 * 
	 * @param userId
	 *            userId
	 * @param participantId
	 *            participantId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             if user is not a participant of this meeting
	 */
	public void checkIfUserIsParticipant(int userId, int participantId) throws IOException, CustomServerException {
		Participant participantForMeetingId = participantWithDao.createParticipantWithDao(participantId);
		User user = userWithDao.createUserWithDao(userId);
		Meeting meetingCheckParticipant = meetingWithDao.createMeetingWithDao(participantForMeetingId.getMeetingId(),
				participantForMeetingId.getUser().getId());
		meetingCheckParticipant.isParticipant(user);
	}

	/**
	 * checks, if user is already participant of this meeting
	 * 
	 * @param participants
	 *            all participants of this meeting
	 * @param userId
	 *            user who has to be added to the meeting
	 * @throws CustomServerException
	 *             if user is already participant of this meeting
	 */
	public void checkIfAlreadyParticipant(List<Participant> participants, int userId) throws CustomServerException {
		for (Participant participant : participants) {
			if (participant.getUser().getId() == userId) {
				throw new CustomServerException("The user is already a participant of this meeting!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

}
