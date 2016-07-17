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
import javax.servlet.http.HttpSession;

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
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupsServlet() {
		super();
	}

	/**
	 * Get All Groups
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		}
	}

	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		HttpSession session = request.getSession(true);

		int userId = 1;// (int) session.getAttribute("userId");
		// int userId = (int) session.getAttribute("userId");
		if (userId <= 0) {
			throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userId;
	}

}
