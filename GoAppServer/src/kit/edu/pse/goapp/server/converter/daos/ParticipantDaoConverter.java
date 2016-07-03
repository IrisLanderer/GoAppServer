package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Participant;

public class ParticipantDaoConverter implements DaoConverter<ParticipantDAO> {

	@Override
	public ParticipantDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		Gson gson = new Gson();
		Participant participantJsonObject = gson.fromJson(jsonString, Participant.class);
		ParticipantDAO dao = new ParticipantDaoImpl();
		dao.setParticipantId(participantJsonObject.getParticipantId());
		dao.setUserId(participantJsonObject.getUser().getId());
		// dao.setMeetingId(participantJsonObject.g);
		dao.setConfirmation(participantJsonObject.getConfirmation());
		return dao;
	}

}
