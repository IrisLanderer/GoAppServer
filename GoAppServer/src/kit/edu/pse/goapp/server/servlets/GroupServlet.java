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
	 * Constructor
	 */
	public GroupServlet() {
		super();
	}

	/**
	 * Reads group information
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
	 * Creates a new group
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

	/**
	 * Deletes a group
	 */
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

	/**
	 * Updates group information
	 */
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

	/**
	 * Authenticates an user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId userId
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

	/**
	 * Creates a group
	 * 
	 * @param groupId
	 *            groupId
	 * @return group group
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private Group createGroupWithDao(int groupId) throws IOException, CustomServerException {
		GroupDAO dao = new GroupDaoImpl();
		dao.setGroupId(groupId);
		Group group = dao.getGroupByID();
		return group;
	}

	/**
	 * Creates an user
	 * 
	 * @param userId
	 *            userId
	 * @return user user
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private User createUserWithDao(int userId) throws IOException, CustomServerException {
		UserDAO userDao = new UserDaoImpl();
		userDao.setUserId(userId);
		User user = userDao.getUserByID();
		return user;
	}

	/**
	 * Checks if the user is group member and group admin
	 * 
	 * @param userId
	 *            userId
	 * @param groupId
	 *            groupId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private void checkIfUserIsMemberAndAdmin(int userId, int groupId) throws IOException, CustomServerException {
		User user = createUserWithDao(userId);
		Group groupCheckingAuthorization = createGroupWithDao(groupId);
		groupCheckingAuthorization.isMember(user);
		groupCheckingAuthorization.isAdmin(user);
	}
}
