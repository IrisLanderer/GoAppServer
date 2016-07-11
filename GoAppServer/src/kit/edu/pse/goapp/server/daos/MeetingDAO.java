/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Meeting;

public interface MeetingDAO {

	public void addMeeting() throws IOException;

	public List<Meeting> getAllMeetings() throws IOException;

	public void updateMeeting() throws IOException;

	public void deleteMeeting() throws IOException;

	public Meeting getMeetingByID() throws IOException;

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
