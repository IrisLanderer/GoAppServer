package kit.edu.pse.goapp.server.converter.daos;

import kit.edu.pse.goapp.server.daos.MeetingDAO;

public class MeetingDaoConverter implements DaoConverter<MeetingDAO> {

	@Override
	public MeetingDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}

		// Gson gson = new Gson();
		// Meeting meetingJsonObject = gson.fromJson(jsonString, Meeting.class);
		// MeetingDAO dao = new MeetingDaoImpl();
		// dao.setName(meetingJsonObject.getName());
		// dao.setGroupId(meetingJsonObject.getId());
		// return dao;
		return null;
	}

}
