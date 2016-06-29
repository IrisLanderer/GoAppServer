package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Meeting;

public interface MeetingDAO {
	
	public void addMeeting();
	public List<Meeting> getAllMeetings();
	public void updateMeeting();
	public void deleteMeeting();
	public Meeting getMeetingByID();

}
