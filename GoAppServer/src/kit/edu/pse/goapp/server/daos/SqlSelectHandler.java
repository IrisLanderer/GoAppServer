package kit.edu.pse.goapp.server.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlSelectHandler {

	public void handleResultSet(ResultSet resultSet) throws SQLException;
}
