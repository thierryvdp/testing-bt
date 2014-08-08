 package lucie.object;

import lucie.interfaces.INeuronLocation;


public class NeuronLocation implements INeuronLocation {
	
	private long x;
	private long y;
	private long z;
	
	public NeuronLocation(long _x,long _y,long _z) {
		x=_x;
		y=_y;
		z=_z;
	}
	
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
		
	}
	public void setY(long y) {
		this.y = y;
	}

	public long getZ() {
		return z;
	}
	public void setZ(long z) {
		this.z = z;
	}
	
}
