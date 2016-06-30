package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;

public class TourConverter implements ObjectConverter<Tour> {

	@Override
	public String serialize(Tour tour) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(tour);
		return jsonString;
	}

	

}
