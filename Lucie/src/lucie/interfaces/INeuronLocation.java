package lucie.interfaces;


public interface INeuronLocation {
	
	
	/**
	 * method to return the x coordinate (right/left)
	 * 
	 * @return long x
	 */
	public long getX();

	/**
	 * set the x coordinate (right/left)
	 * 
	 * @param _x the x coordinate (right/left)
	 */
	public void setX(long _x);
	
	/**
	 * method to return the y coordinate (up/down)
	 * 
	 * @return long y
	 */
	public long getY();

	/**
	 * set the y coordinate (up/down)
	 * 
	 * @param _y the y coordinate (up/down)
	 */
	public void setY(long _y);
	
	/**
	 * method to return the z coordinate (forward/backward)
	 * 
	 * @return long z
	 */
	public long getZ();

	/**
	 * set the z coordinate (forward/backward)
	 * 
	 * @param _z the z coordinate (forward/backward)
	 */
	public void setZ(long _z);
	
}
