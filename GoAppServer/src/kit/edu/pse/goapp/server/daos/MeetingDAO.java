/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public interface MeetingDAO {

	public void addMeeting() throws IOException;

	public List<Meeting> getAllMeetings() throws IOException, CustomServerException;

	public void updateMeeting() throws IOException, CustomServerException;

	public void deleteMeeting() throws IOException, CustomServerException;

	public Meeting getMeetingByID() throws IOException, CustomServerException;

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

	void setUserId(int userId);

}
