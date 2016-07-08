package kit.edu.pse.goapp.server.daos;

import java.util.HashMap;
import java.util.Map;

import kit.edu.pse.goapp.server.datamodels.GPS;

public class GpsDaoImpl implements GPS_DAO {
	
	private static Map<Integer, GPS> map = new HashMap<Integer, GPS>();
     private int userId;
     private GPS gps;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	@Override
	public void userSetGPS() {
	    map.put(userId,gps);
		
	}

	@Override
	public GPS userGetGPS() {
		return map.get(userId);
	}

}
