/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.converter.daos;

import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Interface DaoConverter
 * 
 * @param <T>
 *            object to convert
 */
public interface DaoConverter<T> {

    /**
     * parses jsonString to object
     * 
     * @param jsonString
     *            jsonString
     * @return T object
     * @throws CustomServerException
     *             CustomServerException
     */
    public T parse(String jsonString) throws CustomServerException;

}
