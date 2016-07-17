/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.daos;

import java.util.HashMap;
import java.util.Map;

import kit.edu.pse.goapp.server.datamodels.GPS;

/**
 * Implements GPS Dao
 */
public class GpsDaoImpl implements GPS_DAO {

    private static Map<Integer, GPS> map = new HashMap<Integer, GPS>();
    private int userId;
    private GPS gps;

    /**
     * Returns userId
     * 
     * @return userId userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set userId
     * 
     * @param userId
     *            userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Return GPS position
     * 
     * @return gps GPS position
     */
    public GPS getGps() {
        return gps;
    }

    /**
     * Set GPS position
     * 
     * @param gps
     *            GPS position
     */
    public void setGps(GPS gps) {
        this.gps = gps;
    }

    /**
     * Set GPS position of an user on the map
     */
    @Override
    public void userSetGPS() {
        map.put(userId, gps);

    }

    /**
     * Return GPS position of an user
     * 
     * @return GPS position of the user on the map
     */
    @Override
    public GPS userGetGPS() {
        return map.get(userId);
    }

}
