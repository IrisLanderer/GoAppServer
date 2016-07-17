/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.converter.daos;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GPS_DAO;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public class GpsDaoConverter implements DaoConverter<GPS_DAO> {

	@Override
	public GPS_DAO parse(String jsonString) throws CustomServerException {
		if (jsonString == null) {
			return null;
		}
		GPS gpsJsonObject = null;
		try {
			Gson gson = new Gson();
			gpsJsonObject = gson.fromJson(jsonString, GPS.class);
		} catch (Exception e) {
			throw new CustomServerException("The JSON-String is not correct!", HttpServletResponse.SC_BAD_REQUEST);
		}
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setGps(gpsJsonObject);
		return dao;
	}

}
