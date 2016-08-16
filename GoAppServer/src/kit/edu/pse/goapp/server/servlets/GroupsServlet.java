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

	/**
	 * Constructor
	 */
	public GroupsServlet() {
		super();
	}

	/**
	 * Get All Groups
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			GroupDAO dao = new GroupDaoImpl();
			dao.setUserId(userId);
			if (dao != null) {
				List<Group> groups = dao.getAllGroups();
				response.getWriter().write(
						new ObjectConverter<List<Group>>().serialize(groups, (Class<List<Group>>) groups.getClass()));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Authenticates an user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		CookieManager cm = new CookieManager();

		String userIDString = cm.searchCookie(request, "userId");
		if (userIDString.length() > 0) {
			int userId = Integer.parseInt(userIDString);
			if (userId > 0) {
				return userId;
			}
		}
		throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
	}

}
