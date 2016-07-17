/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.daos;

import kit.edu.pse.goapp.server.datamodels.GPS;

/**
 * Interface GPS DAO
 */
public interface GPS_DAO {

    /**
     * Set GPS position of an user
     */
    public void userSetGPS();

    /**
     * Return GPS position of an user
     * 
     * @return gps GPS position
     */
    public GPS userGetGPS();

}
