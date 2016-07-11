/**
 * 
 */
package kit.edu.pse.goapp.server.exceptions;

public class CustomServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private int statusCode;

	public CustomServerException(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return Integer.toString(statusCode) + " " + message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
