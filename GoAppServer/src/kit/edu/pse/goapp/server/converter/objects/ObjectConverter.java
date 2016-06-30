package kit.edu.pse.goapp.server.converter.objects;

import com.google.gson.Gson;


public class ObjectConverter<T> {
	
	public String serialize(T object) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		return jsonString;
	}

}
