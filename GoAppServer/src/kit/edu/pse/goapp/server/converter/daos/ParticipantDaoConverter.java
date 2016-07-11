/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.converter.daos;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public class ParticipantDaoConverter implements DaoConverter<ParticipantDAO> {

	@Override
	public ParticipantDAO parse(String jsonString) throws CustomServerException {
		if (jsonString == null) {
			return null;
		}
		Participant participantJsonObject = null;
		Gson gson = new Gson();
		try {
			participantJsonObject = gson.fromJson(jsonString, Participant.class);
		} catch (Exception e) {
			throw new CustomServerException("The JSON-String is not correct!", HttpServletResponse.SC_BAD_REQUEST);
		}
		ParticipantDAO dao = new ParticipantDaoImpl();
		dao.setParticipantId(participantJsonObject.getParticipantId());
		dao.setUserId(participantJsonObject.getUser().getId());
		dao.setMeetingId(participantJsonObject.getMeetingId());
		dao.setConfirmation(participantJsonObject.getConfirmation());
		return dao;
	}

}
