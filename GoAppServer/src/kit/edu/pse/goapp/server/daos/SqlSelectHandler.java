/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public interface SqlSelectHandler {

	public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException, IOException;
}
