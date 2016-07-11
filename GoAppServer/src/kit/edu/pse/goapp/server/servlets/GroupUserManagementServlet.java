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

import kit.edu.pse.goapp.server.converter.daos.GroupMemberDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
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
			String groupId = request.getParameter("groupId");
			GroupMemberDAO dao = new GroupMemberDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
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
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberDao = new GroupMemberDaoConverter().parse(jsonString);
			// every group member is an admin automatically (priority A
			// requirement)
			groupMemberDao.setAdmin(true);
			if (groupMemberDao != null) {
				groupMemberDao.addMember();
			}

			GroupDAO groupDao = new GroupDaoImpl();
			groupDao.setGroupId(groupMemberDao.getGroupId());
			Group group = groupDao.getGroupByID();
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
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberdao = new GroupMemberDaoConverter().parse(jsonString);
			if (groupMemberdao != null) {
				groupMemberdao.updateMember();
			}
			GroupDAO groupDao = new GroupDaoImpl();
			groupDao.setGroupId(groupMemberdao.getGroupId());
			Group group = groupDao.getGroupByID();
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
			if (dao != null) {
				dao.deleteMember();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		}

	}
}
