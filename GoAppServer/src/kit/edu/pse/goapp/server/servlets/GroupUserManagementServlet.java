package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String groupId = request.getParameter("groupId");
		GroupMemberDAO dao = new GroupMemberDaoImpl();
		dao.setGroupId(Integer.parseInt(groupId));
		if (dao != null) {
			List<User> groupMembers = dao.getAllMembers();
			response.getWriter().write(new ObjectConverter<List<User>>().serialize(groupMembers,
					(Class<List<User>>) groupMembers.getClass()));
		}
	}

	/**
	 * Add ADmin?
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberDao = new GroupMemberDaoConverter().parse(jsonString);
			if (groupMemberDao != null) {
				groupMemberDao.addMember();
			} else {
				throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
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
	 * AddUser
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String jsonString = request.getReader().readLine();
			GroupMemberDAO groupMemberdao = new GroupMemberDaoConverter().parse(jsonString);
			if (groupMemberdao != null) {
				groupMemberdao.updateMember();
			} else {
				throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
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
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String groupId = request.getParameter("groupId");
		String userId = request.getParameter("userId");
		GroupMemberDAO dao = new GroupMemberDaoImpl();
		dao.setGroupId(Integer.parseInt(groupId));
		dao.setUserId(Integer.parseInt(userId));
		if (dao != null) {
			dao.deleteMember();
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
