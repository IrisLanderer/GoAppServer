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

import kit.edu.pse.goapp.server.converter.daos.GroupMemberDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class GroupUserManagement
 */
@WebServlet("/GroupUserManagement")
public class GroupUserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupUserManagementServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String groupId = request.getParameter("groupId");
			GroupMemberDAO dao = new GroupMemberDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			User user = createUserWithDao(userId);
			Group groupCheckingMember = createGroupWithDao(dao.getGroupId());
			groupCheckingMember.isMember(user);
			if (dao != null) {
				List<User> groupMembers = dao.getAllMembers();
				response.getWriter().write(new ObjectConverter<List<User>>().serialize(groupMembers,
						(Class<List<User>>) groupMembers.getClass()));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		}
	}

	/**
	 * Add user as member
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberDao = new GroupMemberDaoConverter().parse(jsonString);
			// every group member is an admin automatically (priority A
			// requirement)
			groupMemberDao.setAdmin(true);
			checkIfUserIsMemberAndAdmin(userId, groupMemberDao.getGroupId());
			if (groupMemberDao != null) {
				groupMemberDao.addMember();
			}
			Group group = createGroupWithDao(groupMemberDao.getGroupId());
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		}
	}

	/**
	 * add member as admin
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberdao = new GroupMemberDaoConverter().parse(jsonString);
			checkIfUserIsMemberAndAdmin(userId, groupMemberdao.getGroupId());
			if (groupMemberdao != null) {
				groupMemberdao.updateMember();
			}
			Group group = createGroupWithDao(groupMemberdao.getGroupId());
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		}
	}

	/**
	 * Delete user+Admin
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int myUserId = authenticateUser(request);
			String groupId = request.getParameter("groupId");
			String userId = request.getParameter("userId");
			GroupMemberDAO dao = new GroupMemberDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			try {
				dao.setUserId(Integer.parseInt(userId));
			} catch (Exception e) {
				throw new CustomServerException("The UserID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			checkIfUserIsMemberAndAdmin(myUserId, dao.getGroupId());
			if (dao != null) {
				dao.deleteMember();
			}
			response.setStatus(HttpServletResponse.SC_OK);
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
