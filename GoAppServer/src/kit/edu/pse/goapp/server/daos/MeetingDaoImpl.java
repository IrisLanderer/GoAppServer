package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Meeting;

public class MeetingDaoImpl implements MeetingDAO {

	private int meetingId;
	private String name;
	private double placeX;
	private double placeY;
	private double placeZ;
	private long timestamp;
	private int duration;
	private String type;
	private int creatorId;

	public MeetingDaoImpl() {
		super();
	}

	@Override
	public void addMeeting() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Meeting> getAllMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMeeting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMeeting() {
		// TODO Auto-generated method stub

	}

	@Override
	public Meeting getMeetingByID() {
		// TODO Auto-generated method stub
		return null;
	}

}
