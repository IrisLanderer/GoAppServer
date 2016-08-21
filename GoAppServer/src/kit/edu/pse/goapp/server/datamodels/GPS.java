
package kit.edu.pse.goapp.server.datamodels;

import java.util.List;

/**
 * Datamodel of GPS Position of users
 */
public class GPS {
	private double x;
	private double y;
	private double z;

	/**
	 * Calculates the distance between two GPS positions
	 * 
	 * @param first
	 *            first GPS position
	 * @param second
	 *            second GPS position
	 * @return distanceTotal distance between first GPS position and second GPS
	 *         place
	 */
	public static double distance(GPS first, GPS second) {
		if (first == null || second == null)
			return Integer.MAX_VALUE;
		double distanceX = first.x - second.x;
		double distanceY = first.y - second.y;
		double distanceZ = first.z - second.z;
		double distanceTotal = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2) + Math.pow(distanceZ, 2));
		return distanceTotal;
	}

	/**
	 * Returns if two GPS positions are close. Two GPS positions are close if
	 * their distance is smaller than a radius
	 * 
	 * @param first
	 *            first GPS position
	 * @param second
	 *            second GPS position
	 * @param radius
	 *            maximal distance of two close GPS position
	 * @return boolean true if the GPS position are close, false else
	 */
	public static boolean isClose(GPS first, GPS second, double radius) {
		if (first == null || second == null)
			return false;
		if (distance(first, second) <= radius) {
			return true;
		}
		return false;
	}

	/**
	 * Calculates the median of a list of GPS positions
	 * 
	 * @param positions
	 * @return
	 */
	public static GPS median(List<GPS> positions) {
		double x = 0.0;
		double y = 0.0;
		double z = 0.0;
		int count = 0;
		if (positions != null)
			for (GPS position : positions) {
				if (positions != null) {
					x += position.getX();
					y += position.getY();
					z += position.getZ();
					count++;
				}

			}
		x /= (positions.size() - count);
		y /= (positions.size() - count);
		z /= (positions.size() - count);

		return new GPS(x, y, z);

	}

	/**
	 * Constructor. Sets GPS coordinates.
	 * 
	 * @param x
	 *            GPS x coordinate
	 * @param y
	 *            GPS y coordinate
	 * @param z
	 *            GPS z coordinate
	 */
	public GPS(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns GPS x coordinate.
	 * 
	 * @return x GPS x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns GPS y coordinate.
	 * 
	 * @return y GPS y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns GPS z coordinate
	 * 
	 * @return z GPS z coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Equals an object to this GPS position
	 * 
	 * @param obj
	 *            Object to compare
	 * @return boolean true if object is equal to this GPS position, else false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		// Class name is Employ & have lastname
		GPS g = (GPS) obj;
		if (g.getX() == x && g.getY() == y && g.getZ() == z) {
			return true;
		}
		return false;
	}
}
