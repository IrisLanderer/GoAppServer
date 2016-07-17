/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.sql.Connection;
import java.sql.ResultSet;

import org.junit.Test;
import org.mockito.Mockito;

import com.mysql.jdbc.Statement;

import kit.edu.pse.goapp.server.daos.DatabaseConnection;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class DatabaseConnectionTest extends DatabaseConnection {

	public DatabaseConnectionTest() throws Exception {
		super();
	}

	@Test
	public void testInsert() throws Exception {
		// Stubbing behavior
		Connection connMock = Mockito.mock(Connection.class);
		Statement stmtMock = Mockito.mock(Statement.class);
		ResultSet rSetMock = Mockito.mock(ResultSet.class);
		Mockito.when(connMock.createStatement()).thenReturn(stmtMock);
		Mockito.when(stmtMock.getGeneratedKeys()).thenReturn(rSetMock);

		try (DatabaseConnection connection = new DatabaseConnectionTest()) {
			connection.setConnection(connMock);
			connection.insert("Test");
			stmtMock.close();
		}
	}

	@Test(expected = CustomServerException.class)
	public void testDeleteFails() throws Exception {
		// Stubbing behavior
		Connection connMock = Mockito.mock(Connection.class);
		Statement stmtMock = Mockito.mock(Statement.class);
		Mockito.when(connMock.createStatement()).thenReturn(stmtMock);

		try (DatabaseConnection connection = new DatabaseConnectionTest()) {
			connection.setConnection(connMock);
			connection.delete("Test");
			stmtMock.close();
		}
	}

	@Test(expected = CustomServerException.class)
	public void testUpdateFails() throws Exception {
		// Stubbing behavior
		Connection connMock = Mockito.mock(Connection.class);
		Statement stmtMock = Mockito.mock(Statement.class);
		Mockito.when(connMock.createStatement()).thenReturn(stmtMock);

		try (DatabaseConnection connection = new DatabaseConnectionTest()) {
			connection.setConnection(connMock);
			connection.update("Test");
			stmtMock.close();
		}
	}

}
