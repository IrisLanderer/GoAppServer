package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.User;

public class GpsConverter implements ObjectConverter<GPS> {

	@Override
	public String serialize(GPS gps) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(gps);
		return jsonString;
	}

}
