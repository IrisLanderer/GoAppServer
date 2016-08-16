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
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.NotificationDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Notification;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Notification
 */
@WebServlet("/Notification")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Authentication authentication = new Authentication();

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificationServlet() {
		super();
	}

	/**
	 * GetNotification
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			NotificationDaoImpl dao = new NotificationDaoImpl();
			dao.setUserId(userId);
			List<Notification> notifications = dao.getNotifications();
			response.getWriter().write(new ObjectConverter<List<Notification>>().serialize(notifications,
					(Class<List<Notification>>) notifications.getClass()));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
