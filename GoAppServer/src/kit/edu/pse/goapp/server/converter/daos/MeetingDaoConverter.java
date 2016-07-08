package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;

public class MeetingDaoConverter implements DaoConverter<MeetingDAO> {

	@Override
	public MeetingDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		Gson gson = new Gson();
		Meeting meetingJsonObject = gson.fromJson(jsonString, Meeting.class);
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(meetingJsonObject.getMeetingId());
		dao.setName(meetingJsonObject.getName());
		dao.setPlaceX(meetingJsonObject.getPlace().getX());
		dao.setPlaceY(meetingJsonObject.getPlace().getY());
		dao.setPlaceZ(meetingJsonObject.getPlace().getZ());
		dao.setTimestamp(meetingJsonObject.getTimespamp());
		dao.setDuration(meetingJsonObject.getDuration());
		if (meetingJsonObject instanceof Event) {
			dao.setType("Event");
		} else {
			dao.setType("Tour");
		}
		dao.setCreatorId(meetingJsonObject.getCreator().getParticipantId());

		return dao;
	}

}
