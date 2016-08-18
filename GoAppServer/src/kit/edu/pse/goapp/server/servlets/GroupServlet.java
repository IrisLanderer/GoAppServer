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

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.converter.daos.GroupDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.creating_obj_with_dao.GroupWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.GroupValidation;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet("/Group")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupWithDao groupWithDao = new GroupWithDao();
	private UserWithDao userWithDao = new UserWithDao();
	private GroupValidation validation = new GroupValidation();
	private Authentication authentication = new Authentication();

	/**
	 * Constructor
	 */
	public GroupServlet() {
		super();
	}

	/**
	 * Reads group information
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response, authentication);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String groupId = request.getParameter("groupId");
			GroupDAO dao = new GroupDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			User user = userWithDao.createUserWithDao(userId);
			Group groupCheckingAuthorization = groupWithDao.createGroupWithDao(dao.getGroupId());
			groupCheckingAuthorization.isMember(user);
			Group group = dao.getGroupByID();
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());

		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Creates a new group
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response, authentication);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
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
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a group
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doDelete(request, response, authentication);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String groupId = request.getParameter("groupId");
			GroupDAO dao = new GroupDaoImpl();
			try {
				dao.setGroupId(Integer.parseInt(groupId));
			} catch (Exception e) {
				throw new CustomServerException("The GroupID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			validation.checkIfUserIsMemberAndAdmin(userId, dao.getGroupId());
			if (dao != null) {
				dao.deleteGroup();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates group information
	 */
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPut(request, response, authentication);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GroupDAO dao = new GroupDaoConverter().parse(jsonString);
			validation.checkIfUserIsMemberAndAdmin(userId, dao.getGroupId());
			if (dao != null) {
				dao.updateGroup();
			}
			Group group = dao.getGroupByID();
			response.getWriter().write(new ObjectConverter<Group>().serialize(group, Group.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
