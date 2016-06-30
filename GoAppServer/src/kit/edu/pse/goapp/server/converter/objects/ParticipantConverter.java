package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;

public class ParticipantConverter implements ObjectConverter<Participant> {

	@Override
	public String serialize(Participant participant) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(participant);
		return jsonString;
	}

	

}
