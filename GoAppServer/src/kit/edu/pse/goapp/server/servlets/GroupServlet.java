/*
 * @version 1.0
 * @author PSE group
 */

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
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String groupId = request.getParameter("groupId");
			GroupDAO dao = new GroupDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			User user = createUserWithDao(userId);
			Group groupCheckingAuthorization = createGroupWithDao(dao.getGroupId());
			groupCheckingAuthorization.isMember(user);
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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupDAO dao = new GroupDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			if (dao != null) {
				dao.addGroup();
			}
			Group group = dao.getGroupByID();
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String groupId = request.getParameter("groupId");
			GroupDAO dao = new GroupDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			checkIfUserIsMemberAndAdmin(userId, dao.getGroupId());
			if (dao != null) {
				dao.deleteGroup();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupDAO dao = new GroupDaoConverter().parse(jsonString);
			checkIfUserIsMemberAndAdmin(userId, dao.getGroupId());
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

	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		HttpSession session = request.getSession(true);

		int userId = 1;// (int) session.getAttribute("userId");
		if (userId <= 0) {
			throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userId;
	}

	private Group createGroupWithDao(int groupId) throws IOException, CustomServerException {
		GroupDAO dao = new GroupDaoImpl();
		dao.setGroupId(groupId);
		Group group = dao.getGroupByID();
		return group;
	}

	private User createUserWithDao(int userId) throws IOException, CustomServerException {
		UserDAO userDao = new UserDaoImpl();
		userDao.setUserId(userId);
		User user = userDao.getUserByID();
		return user;
	}

	private void checkIfUserIsMemberAndAdmin(int userId, int groupId) throws IOException, CustomServerException {
		User user = createUserWithDao(userId);
		Group groupCheckingAuthorization = createGroupWithDao(groupId);
		groupCheckingAuthorization.isMember(user);
		groupCheckingAuthorization.isAdmin(user);
	}
}
