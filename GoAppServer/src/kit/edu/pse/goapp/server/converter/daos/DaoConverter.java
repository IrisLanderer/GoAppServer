/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.converter.daos;

import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public interface DaoConverter<T> {

	public T parse(String jsonString) throws CustomServerException;

}
