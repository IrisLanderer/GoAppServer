package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kit.edu.pse.goapp.server.converter.daos.GroupDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet("/Group")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);

			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}
			String groupId = request.getParameter("groupId");
			GroupDAO dao = new GroupDaoImpl();
			dao.setUserId(userId);
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (dao != null) {
				Group group = dao.getGroupByID();
				response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String jsonString = request.getReader().readLine();
			GroupDAO dao = new GroupDaoConverter().parse(jsonString);
			if (dao != null) {
				dao.addGroup();
			} else {
				throw new CustomServerException("The DataAccessObject is empty!", HttpServletResponse.SC_BAD_REQUEST);
			}

			Group group = dao.getGroupByID();
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);

			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}
			String groupId = request.getParameter("groupId");
			GroupDAO dao = new GroupDaoImpl();
			dao.setUserId(userId);
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (dao != null) {
				dao.deleteGroup();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);

			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}
			String jsonString = request.getReader().readLine();
			GroupDAO dao = new GroupDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			if (dao != null) {
				dao.updateGroup();
			}
			Group group = dao.getGroupByID();
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}
}
