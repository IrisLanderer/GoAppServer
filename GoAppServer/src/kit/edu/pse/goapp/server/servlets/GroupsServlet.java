/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Groups
 */
@WebServlet("/Groups")
public class GroupsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Authentication authentication = new Authentication();

	/**
	 * Constructor
	 */
	public GroupsServlet() {
		super();
	}

	/**
	 * Get All user's groups
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response, authentication);
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			GroupDAO dao = new GroupDaoImpl();
			dao.setUserId(userId);
			List<Group> groups = dao.getAllGroups();
			response.getWriter().write(
					new ObjectConverter<List<Group>>().serialize(groups, (Class<List<Group>>) groups.getClass()));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
