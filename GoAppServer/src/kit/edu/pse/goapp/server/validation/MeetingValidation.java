/**
 * 
 */
package kit.edu.pse.goapp.server.validation;

import java.io.IOException;

import kit.edu.pse.goapp.server.creating_obj_with_dao.MeetingWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.ParticipantWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
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
	 * @param participantId
	 *            participantId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public void checkIfUserIsParticipantAndCreator(int userId, int participantId)
			throws IOException, CustomServerException {
		Participant participantForMeetingId = participantWithDao.createParticipantWithDao(participantId);
		User user = userWithDao.createUserWithDao(userId);
		Meeting meetingCheckParticipant = meetingWithDao.createMeetingWithDao(participantForMeetingId.getMeetingId(),
				participantForMeetingId.getUser().getId());
		meetingCheckParticipant.isParticipant(user);
		meetingCheckParticipant.isCreator(user);
	}

}
