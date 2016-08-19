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
import kit.edu.pse.goapp.server.converter.daos.GpsDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class GpsServlet
 */
@WebServlet("/Gps")
public class GpsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Authentication authentication = new Authentication();

	/**
	 * constructor of the class GpsServlet
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public GpsServlet() {
		super();

	}

	/**
	 * updates the GPS data
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GpsDaoImpl dao = (GpsDaoImpl) new GpsDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			dao.setUserId(userId);
			dao.userSetGPS();
			GPS gps = dao.userGetGPS();
			response.getWriter().write(new ObjectConverter<GPS>().serialize(gps, GPS.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

}
