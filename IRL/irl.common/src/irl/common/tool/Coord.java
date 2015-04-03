package irl.common.tool;

public class Coord {
	
	private Object x;
	private Object y;
	private Object z;
	
	public Coord() {
		x=null;
		y=null;
		z=null;
	}
	
	public Coord(Object _x,Object _y,Object _z) {
		x=_x;
		y=_y;
		z=_z;
	}
	
	public Object getX() {
		return x;
	}
	public void setX(Object x) {
		this.x = x;
	}
	public Object getY() {
		return y;
	}
	public void setY(Object y) {
		this.y = y;
	}
	public Object getZ() {
		return z;
	}
	public void setZ(Object z) {
		this.z = z;
	}
	
	

}
