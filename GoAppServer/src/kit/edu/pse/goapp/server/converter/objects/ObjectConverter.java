package kit.edu.pse.goapp.server.converter.objects;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


public class ObjectConverter<T> {
	
	public String serialize(T object) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		return jsonString;
	}
	

}
