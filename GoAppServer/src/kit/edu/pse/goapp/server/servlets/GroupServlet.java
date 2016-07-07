package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import kit.edu.pse.goapp.server.converter.daos.GroupDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;

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
		String groupId = request.getParameter("groupId");
		GroupDAO dao = new GroupDaoImpl();
		dao.setGroupId(Integer.parseInt(groupId));
		if (dao != null) {
			Group group = dao.getGroupByID();
			response.getWriter().write(new ObjectConverter<Group>().serialize(group));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		GroupDAO dao = new GroupDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.addGroup();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}

		Group group = dao.getGroupByID();
		response.getWriter().write(new ObjectConverter<Group>().serialize(group));
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		GroupDAO dao = new GroupDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.deleteGroup();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		GroupDAO dao = new GroupDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.updateGroup();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		Group group = dao.getGroupByID();
		response.getWriter().write(new ObjectConverter<Group>().serialize(group));
	}
}
