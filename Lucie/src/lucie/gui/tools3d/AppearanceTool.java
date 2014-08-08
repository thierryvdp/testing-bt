package lucie.gui.tools3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;

public class AppearanceTool {

	public static Appearance mkFillApp() {
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		Appearance appearance = new Appearance();
		appearance.setPolygonAttributes(polyAttrib);
		appearance.setMaterial(new Material(new Color3f(0.63f,0.42f,0.21f),new Color3f(0f,0f,0f),new Color3f(0.63f,0.42f,0.21f),new Color3f(0.63f,0.42f,0.21f),64));		
		return appearance;
	}

}
