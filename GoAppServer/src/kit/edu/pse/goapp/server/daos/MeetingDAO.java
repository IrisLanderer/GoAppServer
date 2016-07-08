package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Meeting;

public interface MeetingDAO {

	public void addMeeting();

	public List<Meeting> getAllMeetings();

	public void updateMeeting();

	public void deleteMeeting();

	public Meeting getMeetingByID();

	void setCreatorId(int creatorId);

	int getCreatorId();

	void setType(String type);

	String getType();

	void setDuration(int duration);

	int getDuration();

	void setTimestamp(long timestamp);

	long getTimestamp();

	void setPlaceZ(double placeZ);

	double getPlaceZ();

	void setPlaceY(double placeY);

	double getPlaceY();

	void setPlaceX(double placeX);

	double getPlaceX();

	void setName(String name);

	String getName();

	void setMeetingId(int meetingId);

	int getMeetingId();

}
