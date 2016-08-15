/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * 
 * interface which is implemented by sql selection handlers which every dao has
 *
 */
public interface SqlSelectHandler {

	/**
	 * handles the received result data from the database
	 * 
	 * @param resultSet
	 *            received data from database
	 * @throws SQLException
	 * @throws CustomServerException
	 * @throws IOException
	 */
	public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException, IOException;
}
