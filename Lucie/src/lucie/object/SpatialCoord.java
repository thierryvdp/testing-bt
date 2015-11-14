package lucie.object;

public class SpatialCoord {

	long x;
	long y;
	long z;
	
	public SpatialCoord() {
		init(0, 0, 0);
	}
	
	public SpatialCoord(long _x,long _y,long _z) {
		init(_x, _y, _z);
	}
	
	private void init(long _x,long _y,long _z) {
		x=_x;
		y=_y;
		z=_z;
	}

	public long getX() {
		return x;
	}
	public long getY() {
		return y;
	}
	public long getZ() {
		return z;
	}

	public void setX(long _x) {
		x = _x;
	}
	public void setY(long _y) {
		y = _y;
	}
	public void setZ(long _z) {
		z = _z;
	}
	
	public SpatialCoord getCopy() {
		return new SpatialCoord(x, y, z);
	}
	
	public String toString() {
		return "(x,y,z)=("+x+","+y+","+z+")";
	}
	
}
