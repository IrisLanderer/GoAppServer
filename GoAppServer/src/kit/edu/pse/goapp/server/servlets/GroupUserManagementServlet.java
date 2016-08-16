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
import kit.edu.pse.goapp.server.converter.daos.GroupMemberDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.creating_obj_with_dao.GroupWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.GroupValidation;

/**
 * Servlet implementation class GroupUserManagement
 */
@WebServlet("/GroupUserManagement")
public class GroupUserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GroupValidation validation = new GroupValidation();

	private GroupWithDao groupWithDao = new GroupWithDao();
	private UserWithDao userWithDao = new UserWithDao();

	private Authentication authentication = new Authentication();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroupUserManagementServlet() {
		super();
	}

	/**
	 * Get group members
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String groupId = request.getParameter("groupId");
			GroupMemberDAO dao = new GroupMemberDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			User user = userWithDao.createUserWithDao(userId);
			Group groupCheckingMember = groupWithDao.createGroupWithDao(dao.getGroupId());
			groupCheckingMember.isMember(user);
			if (dao != null) {
				List<User> groupMembers = dao.getAllMembers();
				response.getWriter().write(new ObjectConverter<List<User>>().serialize(groupMembers,
						(Class<List<User>>) groupMembers.getClass()));
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
	 * Add user as member
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberDao = new GroupMemberDaoConverter().parse(jsonString);
			// every group member is an admin automatically (priority A
			// requirement)
			groupMemberDao.setAdmin(true);
			validation.checkIfUserIsMemberAndAdmin(userId, groupMemberDao.getGroupId());
			if (groupMemberDao != null) {
				groupMemberDao.addMember();
			}
			Group group = groupWithDao.createGroupWithDao(groupMemberDao.getGroupId());
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberdao = new GroupMemberDaoConverter().parse(jsonString);
			validation.checkIfUserIsMemberAndAdmin(userId, groupMemberdao.getGroupId());
			if (groupMemberdao != null) {
				groupMemberdao.updateMember();
			}
			Group group = groupWithDao.createGroupWithDao(groupMemberdao.getGroupId());
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			int myUserId = authentication.authenticateUser(request);
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
			validation.checkIfUserIsMemberAndAdmin(myUserId, dao.getGroupId());
			if (dao != null) {
				dao.deleteMember();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

}
