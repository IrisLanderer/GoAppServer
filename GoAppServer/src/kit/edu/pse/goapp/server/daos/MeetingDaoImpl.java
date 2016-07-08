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

	@Override
	public int getMeetingId() {
		return meetingId;
	}

	@Override
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getPlaceX() {
		return placeX;
	}

	@Override
	public void setPlaceX(double placeX) {
		this.placeX = placeX;
	}

	@Override
	public double getPlaceY() {
		return placeY;
	}

	@Override
	public void setPlaceY(double placeY) {
		this.placeY = placeY;
	}

	@Override
	public double getPlaceZ() {
		return placeZ;
	}

	@Override
	public void setPlaceZ(double placeZ) {
		this.placeZ = placeZ;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getCreatorId() {
		return creatorId;
	}

	@Override
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

}
