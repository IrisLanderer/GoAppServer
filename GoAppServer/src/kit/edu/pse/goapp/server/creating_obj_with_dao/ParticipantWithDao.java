/**
 * 
 */
package kit.edu.pse.goapp.server.creating_obj_with_dao;

import java.io.IOException;

import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
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

}
