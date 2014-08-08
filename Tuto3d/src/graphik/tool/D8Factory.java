package graphik.tool;

import javax.media.j3d.Geometry;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class D8Factory {
	
	private static D8Factory instance;

	private D8Factory() {
	}
	
	public static synchronized D8Factory getInstance() {
		if(instance==null) {
			instance=new D8Factory();
		}
		return instance;
	}
	
	public Geometry getNewD8(float size) {
		int tab[] = new int[1];
		tab[0]=12;
		TriangleStripArray d8=new TriangleStripArray(12,
				TriangleStripArray.COORDINATES|
				TriangleStripArray.COLOR_3|
//				TriangleStripArray.TEXTURE_COORDINATE_2|
				TriangleStripArray.NORMALS,
				tab);
		d8.setCoordinate( 0,getSommet(1, size));
		d8.setCoordinate( 1,getSommet(2, size));
		d8.setCoordinate( 2,getSommet(3, size));
		d8.setCoordinate( 3,getSommet(4, size));
		d8.setCoordinate( 4,getSommet(5, size));
		d8.setCoordinate( 5,getSommet(6, size));
		d8.setCoordinate( 6,getSommet(4, size));
		d8.setCoordinate( 7,getSommet(2, size));
		d8.setCoordinate( 8,getSommet(6, size));
		d8.setCoordinate( 9,getSommet(1, size));
		d8.setCoordinate(10,getSommet(5, size));
		d8.setCoordinate(11,getSommet(3, size));
		for(int i=0;i<12;i++) d8.setColor(i,new Color3f(1f,0f,1f));		
		return d8;
	}
	
	private Point3f getSommet(int numero,float size) {
		
		float un=1/size;
		switch (numero) {
		case 1:
			return new Point3f(-1*un, 0, -1*un);
		case 2:
			return new Point3f(0, un, 0);
		case 3:
			return new Point3f(un, 0, -1*un);
		case 4:
			return new Point3f(un, 0, un);
		case 5:
			return new Point3f(0, -1*un, 0);
		case 6:
			return new Point3f(-1*un, 0, un);
		}
		return null;
	}
	
}
