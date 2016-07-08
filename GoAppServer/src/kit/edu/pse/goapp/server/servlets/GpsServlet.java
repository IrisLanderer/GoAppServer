package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import kit.edu.pse.goapp.server.converter.daos.GpsDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GPS_DAO;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GPS;

/**
 * Servlet implementation class GpsServlet
 */
@WebServlet("/Gps")
public class GpsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GpsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		int userId = 1;// (int) session.getAttribute("userId");
		if (userId <= 0) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		String jsonString = request.getReader().readLine();
		GPS_DAO dao = new GpsDaoConverter().parse(jsonString);
		if (dao != null) {
			((GpsDaoImpl) dao).setUserId(userId);
			dao.userSetGPS();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		GPS gps = dao.userGetGPS();
		response.getWriter().write(new ObjectConverter<GPS>().serialize(gps));
	}

}
