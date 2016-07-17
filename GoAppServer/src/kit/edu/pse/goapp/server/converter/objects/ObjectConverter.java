/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.converter.objects;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Converts objects from database to parameters before sending to servlet and
 * reverse.
 *
 * @param <T>
 *            Object T
 */
public class ObjectConverter<T> {

    /**
     * Converts objects from database into a json string and sends it to
     * servlet.
     * 
     * @param object
     *            Object to convert
     * @param classType
     *            Class which contains object
     * @return json String
     * @throws CustomServerException
     *             CustomServerException
     */
    public String serialize(T object, Class<T> classType)
            throws CustomServerException {
        RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Meeting.class).registerSubtype(Event.class)
                .registerSubtype(Tour.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        String json = null;
        try {
            json = gson.toJson(object, classType);
        } catch (Exception e) {
            throw new CustomServerException("The JSON-String is not correct!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        return json;
    }

    /**
     * Converts JsonStrings from servlets into objects and sends them to
     * database.
     * 
     * @param json
     *            jsonString to convert
     * @param classType
     *            Type of object
     * @return T object
     * @throws CustomServerException
     *             CustomServerException
     */
    public T deserialize(String json, Class<T> classType)
            throws CustomServerException {
        RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Meeting.class).registerSubtype(Event.class)
                .registerSubtype(Tour.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        try {
            return gson.fromJson(json, classType);
        } catch (Exception e) {
            throw new CustomServerException("The JSON-String is not correct!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Converts JsonStrings from servlets into a list and returns the list to
     * database.
     * 
     * @param json
     *            jsonString
     * @param clazz
     *            Type of object
     * @return list of objects
     */
    public <T> List<T> deserializeList(String json, Class<T[]> clazz) {
        RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Meeting.class).registerSubtype(Event.class)
                .registerSubtype(Tour.class);
        T[] arr = new Gson().fromJson(json, clazz);
        return Arrays.asList(arr);
    }

}
