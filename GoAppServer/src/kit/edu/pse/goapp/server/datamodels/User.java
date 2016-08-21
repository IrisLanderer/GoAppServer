/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Database of an User
 */
public class User {
	private int userId;
	private String name;

	private GPS gps;
	private boolean notificationEnabled;

	private List<Meeting> meetings;
	private List<Group> groups;

	/**
	 * Constructor. Sets variables of the user
	 * 
	 * @param userId
	 *            ID of the user
	 * @param name
	 *            name of the user
	 */
	public User(int userId, String name) {
		this.userId = userId;
		this.name = name;
		meetings = new ArrayList<Meeting>();
		groups = new ArrayList<Group>();
	}

	/**
	 * Returns userId
	 * 
	 * @return userId userId
	 */
	public int getId() {
		return userId;
	}

	/**
	 * Returns username
	 * 
	 * @return name username
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets GPS position
	 * 
	 * @param gps
	 *            GPS position
	 */
	public void setGPS(GPS gps) {
		this.gps = gps;
	}

	public GPS getGps() {
		return gps;
	}

	/**
	 * Create a new group
	 * 
	 * @param group
	 *            new group
	 */
	public void addGroup(Group group) {
		groups.add(group);
	}

	/**
	 * Create a new meeting
	 * 
	 * @param meeting
	 *            new meeting
	 */
	public void addMeeting(Meeting meeting) {
		meetings.add(meeting);
	}

	/**
	 * Returns list of meetings
	 * 
	 * @return meetings list of meetings
	 */
	public List<Meeting> getAllMeetigns() {
		return meetings;
	}

	/**
	 * Return list of all groups
	 * 
	 * @return groups list of all groups;
	 */
	public List<Group> getAllGroups() {
		return groups;
	}

	/**
	 * Shows if notifications are enabled
	 * 
	 * @return notificationEnabled true if notifications are enabled, else false
	 */
	public boolean isNotificationEnabled() {
		return notificationEnabled;
	}

	/**
	 * Set notification status as enabled
	 * 
	 * @param notificationEnabled
	 *            notifications enabled;
	 */
	public void setNotificationEnabled(boolean notificationEnabled) {
		this.notificationEnabled = notificationEnabled;
	}

	/**
	 * Equals an object to this user
	 * 
	 * @param obj
	 *            Object to compare
	 * @return boolean true if object is equal to this user, else false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		// Class name is Employ & have lastname
		User u = (User) obj;
		if ((u.getId() == userId) && (u.getName().equals(name))) {
			return true;
		}

		return false;
	}

}
