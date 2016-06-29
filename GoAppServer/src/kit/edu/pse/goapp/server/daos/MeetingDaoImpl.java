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

	public MeetingDaoImpl(int meetingId, String name, double placeX, double placeY, double placeZ, long timestamp,
			int duration, String type, int creatorId) {
		super();
		this.meetingId = meetingId;
		this.name = name;
		this.placeX = placeX;
		this.placeY = placeY;
		this.placeZ = placeZ;
		this.timestamp = timestamp;
		this.duration = duration;
		this.type = type;
		this.creatorId = creatorId;
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
