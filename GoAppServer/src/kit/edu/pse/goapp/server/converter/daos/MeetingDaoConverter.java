/*
 * @version 1.0
 * @author Iris
 */

package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;

public class MeetingDaoConverter implements DaoConverter<MeetingDAO> {

	@Override
	public MeetingDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		Gson gson = new Gson();
		ObjectConverter<Meeting> converter = new ObjectConverter<>();
		Meeting meetingJsonObject = converter.deserialize(jsonString, Meeting.class);
		MeetingDaoImpl dao = new MeetingDaoImpl();
		dao.setUserId(1);
		dao.setMeetingId(meetingJsonObject.getMeetingId());
		dao.setName(meetingJsonObject.getName());
		dao.setPlaceX(meetingJsonObject.getPlace().getX());
		dao.setPlaceY(meetingJsonObject.getPlace().getY());
		dao.setPlaceZ(meetingJsonObject.getPlace().getZ());
		dao.setTimestamp(meetingJsonObject.getTimespamp());
		dao.setDuration(meetingJsonObject.getDuration());
		if (meetingJsonObject instanceof Tour) {
			dao.setType("Tour");
		} else {
			dao.setType("Event");
		}
		dao.setCreatorId(meetingJsonObject.getCreator().getParticipantId());

		return dao;
	}

}
