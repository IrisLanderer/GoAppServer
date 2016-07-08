package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GPS_DAO;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GPS;




public class GpsDaoConverter implements DaoConverter<GPS_DAO> {

	@Override
	public GPS_DAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		
		Gson gson = new Gson();
		GPS gpsJsonObject = gson.fromJson(jsonString, GPS.class);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setGps(gpsJsonObject);	
		return dao;
	}

}
