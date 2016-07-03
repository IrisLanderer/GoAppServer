package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.datamodels.Meeting;

public class MeetingDaoConverter implements DaoConverter<MeetingDAO> {

	@Override
	public MeetingDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		Gson gson = new Gson();
		Meeting meetingJsonObject = gson.fromJson(jsonString, Meeting.class);
		return null;
	}

}
