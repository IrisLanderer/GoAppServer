/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.converter.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;

public class ObjectConverter<T> {

	public String serialize(T object, Class<T> classType) {
		RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Meeting.class)
				.registerSubtype(Event.class).registerSubtype(Tour.class);
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
		String json = gson.toJson(object, classType);
		return json;
	}

	public T deserialize(String json, Class<T> classType) {
		RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Meeting.class)
				.registerSubtype(Event.class).registerSubtype(Tour.class);
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();

		return gson.fromJson(json, classType);
	}

}
