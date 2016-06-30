package kit.edu.pse.goapp.server.converter.objects;


import com.google.gson.Gson;

import kit.edu.pse.goapp.server.datamodels.Event;


public class EventConverter implements ObjectConverter<Event> {

	
	@Override
	public String serialize(Event event) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(event);
		return jsonString;
	}


}
