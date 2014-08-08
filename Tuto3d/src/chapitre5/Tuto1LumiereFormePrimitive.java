package chapitre5;

/* Chapitre 5: Section 1, textures et lumières sur des primitives
 * Auteur    : Daboul
 * Date      : 05/00
 */

import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
//api Java3d
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Material;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.SpotLight;
import javax.media.j3d.PointLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Texture;
import javax.media.j3d.ImageComponent2D;
import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

class Tuto1LumiereFormePrimitive extends Frame implements WindowListener
{
	Tuto1LumiereFormePrimitive(boolean ambientBool,boolean directBool,boolean pointBool,boolean texBool)
	{
		super("- Chapiter 5: lumières et textures -");
		this.setLayout(new BorderLayout());
		
		Canvas3D myCan=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld=new SimpleUniverse(myCan);
		myWorld.addBranchGraph(createScene(ambientBool,directBool,pointBool,texBool));
		myWorld.getViewingPlatform().setNominalViewingTransform();
		
		this.addWindowListener(this);
		this.add("Center",myCan);
	}
	
	private BranchGroup createScene(boolean ambientBool,boolean directBool,boolean pointBool,boolean texBool)
	{
		BranchGroup myScene=new BranchGroup();
		
	//on manipule l'objet Appearance 
		Appearance boxApp=new Appearance();
		
		//polygon Attributes
		PolygonAttributes polyAt=new PolygonAttributes
			(PolygonAttributes.POLYGON_FILL
			,PolygonAttributes.CULL_NONE
			,0f);
		boxApp.setPolygonAttributes(polyAt);
		
		//material -> uniquement si il y a des lumières
		Material mat=new Material
			(new Color3f(1f,1f,0f)
			,new Color3f(0f,0f,0f)
			,new Color3f(1f,1f,0f)
			,new Color3f(1f,1f,1f)
			,64);
		boxApp.setMaterial(mat);
		
		Box box=new Box(0.4f,0.4f,0.4f,Box.GENERATE_TEXTURE_COORDS|Box.GENERATE_NORMALS,boxApp);
		
		//rotation de l'objet
		Alpha rotationAlpha=new Alpha(-1, 15000);
         	TransformGroup objSpin=new TransformGroup();
        	objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        	RotationInterpolator rotator=new RotationInterpolator(rotationAlpha,objSpin);
        	rotator.setSchedulingBounds(new BoundingSphere());
        	objSpin.addChild(rotator);		
		objSpin.addChild(box);
		
		myScene.addChild(objSpin);
				
		//texture Attributes
		if (texBool==true) addTexture(boxApp,"stripe.gif");
				
		//lumières
		if(ambientBool)
		{
			AmbientLight ambLi=new AmbientLight(new Color3f(1f,1f,1f));
			ambLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
			myScene.addChild(ambLi);
		}
		if(directBool)
		{
			DirectionalLight diLi=new DirectionalLight();
			diLi.setDirection(new Vector3f(0f,0f,-1f));
			diLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
			diLi.setColor(new Color3f(1f,1f,1f));
			myScene.addChild(diLi);
		}
		if(pointBool)
		{
			PointLight pointLi=new PointLight
				(new Color3f(1f,1f,1f)
				,new Point3f(2f,2f,2f)
				,new Point3f(1f,0f,0f));
			pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
			myScene.addChild(pointLi);
		}
				
		myScene.compile();
		return myScene;
	}
	
	void addTexture(Appearance app,String textureName)
	{
		TextureLoader loader=new TextureLoader(textureName,this);
        	ImageComponent2D image=loader.getImage();
        	
        	Texture2D texture=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
        	texture.setImage(0, image);
        	texture.setEnable(true);
        	texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        	texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        	app.setTexture(texture);
        	TextureAttributes textureAt=new TextureAttributes();
        	textureAt.setTextureMode(TextureAttributes.MODULATE);
        	app.setTextureAttributes(textureAt);
        }
	
	public void windowOpened(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
	
	public static void main(String arg[])
	{
		Tuto1LumiereFormePrimitive myApp=new Tuto1LumiereFormePrimitive(false,false,true,true);
		myApp.setSize(350,350);
		myApp.setVisible(true);
	}
}