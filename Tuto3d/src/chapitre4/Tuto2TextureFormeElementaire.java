package chapitre4;

/* Chapitre 4: Section 2, texturation de formes élementaires
 * Auteur    : Daboul
 * Date      : 04/00
 */

//Java standart API
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
//Java 3d API
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleStripArray;
import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.TextureAttributes;
import com.sun.j3d.utils.image.TextureLoader;
import javax.vecmath.Point3f;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;

class Tuto2TextureFormeElementaire extends Frame implements WindowListener
{
	Tuto2TextureFormeElementaire()
	{
		super("-Chapitre 4: Creation et texturation d'un objet elementaire -");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld=new SimpleUniverse(canvas);
		BranchGroup myScene=createScene1();
		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		//fin de creation
		
		this.add("Center",canvas);
	}
	
	private void setTextCoord(int xdiv,int ydiv,TriangleStripArray zone)
	{
		zone.setTextureCoordinate(0,new Point2f((float)xdiv,0f));
		zone.setTextureCoordinate(1,new Point2f((float)xdiv,(float)ydiv));
		zone.setTextureCoordinate(2,new Point2f(0f,0f));
		zone.setTextureCoordinate(3,new Point2f(0f,(float)ydiv));
	}
	
	BranchGroup createScene1()
	{
		BranchGroup scene=new BranchGroup();
				
		//creation des 4 geometries
		Shape3D obj0=new Shape3D(createBarriere(),createAppearance("point"));
		Shape3D obj1=new Shape3D(createBarriere(),createAppearance("line"));
		Shape3D obj2=new Shape3D(createBarriere(),createAppearance(""));
		Shape3D obj3=new Shape3D(createBarriere(),createTextureApp("oak.jpg"));
						
		//mise en place des objets
		TransformGroup tr0=mkTranslation(new Vector3f(-0.6f,0.35f,0f));
		TransformGroup tr1=mkTranslation(new Vector3f(-0.2f,0.35f,0f));
		TransformGroup tr2=mkTranslation(new Vector3f(0.2f,0.35f,0f));
		TransformGroup tr3=mkTranslation(new Vector3f(0.6f,0.35f,0f));
						
		TransformGroup objSpin0=mkSpin();
		TransformGroup objSpin1=mkSpin();
		TransformGroup objSpin2=mkSpin();
		TransformGroup objSpin3=mkSpin();
        	        	
        	objSpin0.addChild(obj0);
        	tr0.addChild(objSpin0);
        	objSpin1.addChild(obj1);
        	tr1.addChild(objSpin1);
        	objSpin2.addChild(obj2);
        	tr2.addChild(objSpin2);
        	objSpin3.addChild(obj3);
        	tr3.addChild(objSpin3);
        	
        	scene.addChild(tr0);
        	scene.addChild(tr1);
        	scene.addChild(tr2);
        	scene.addChild(tr3);
        			
	        Background bg=mkBackground("bg.gif");
	        scene.addChild(bg);
	        		
		scene.compile();
		return scene;
	}
	
	TransformGroup mkTranslation(Vector3f vect)
	{
		Transform3D t3d=new Transform3D();
		t3d.setTranslation(vect);
		return new TransformGroup(t3d);
	}
	
	TransformGroup mkSpin()
	{
		Alpha rotationAlpha=new Alpha(-1, 5000);
         	BoundingSphere bounds = new BoundingSphere();
		
		TransformGroup objSpin=new TransformGroup();
        	objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        	RotationInterpolator rotator=new RotationInterpolator(rotationAlpha, objSpin);
        	rotator.setSchedulingBounds(bounds);
        	objSpin.addChild(rotator);
        	return objSpin;
	}
	
	//creation d'un element de barriere
	Geometry createBarriere()
	{
		
		int tab[]=new int[4];
		tab[0]=5;
		tab[1]=5;
		tab[2]=5;
		tab[3]=5;
		
		TriangleStripArray objGeom=new TriangleStripArray(20,
			TriangleStripArray.COORDINATES|
			TriangleStripArray.COLOR_3|
			TriangleStripArray.TEXTURE_COORDINATE_2,
			tab);
		
		objGeom.setCoordinate(0,new Point3f(0f,0.1f,0f));
		objGeom.setCoordinate(1,new Point3f(0.05f,0f,0.05f));
		objGeom.setCoordinate(2,new Point3f(-0.05f,0f,0.05f));
		objGeom.setCoordinate(3,new Point3f(0.05f,-0.8f,0.05f));
		objGeom.setCoordinate(4,new Point3f(-0.05f,-0.8f,0.05f));
		
		objGeom.setCoordinate(5,new Point3f(0f,0.1f,0f));
		objGeom.setCoordinate(6,new Point3f(0.05f,0f,-0.05f));
		objGeom.setCoordinate(7,new Point3f(-0.05f,0f,-0.05f));
		objGeom.setCoordinate(8,new Point3f(0.05f,-0.8f,-0.05f));
		objGeom.setCoordinate(9,new Point3f(-0.05f,-0.8f,-0.05f));
		
		objGeom.setCoordinate(10,new Point3f(0f,0.1f,0f));
		objGeom.setCoordinate(11,new Point3f(-0.05f,0f,0.05f));
		objGeom.setCoordinate(12,new Point3f(-0.05f,0f,-0.05f));
		objGeom.setCoordinate(13,new Point3f(-0.05f,-0.8f,0.05f));
		objGeom.setCoordinate(14,new Point3f(-0.05f,-0.8f,-0.05f));
		
		objGeom.setCoordinate(15,new Point3f(0f,0.1f,0f));
		objGeom.setCoordinate(16,new Point3f(0.05f,0f,0.05f));
		objGeom.setCoordinate(17,new Point3f(0.05f,0f,-0.05f));
		objGeom.setCoordinate(18,new Point3f(0.05f,-0.8f,0.05f));
		objGeom.setCoordinate(19,new Point3f(0.05f,-0.8f,-0.05f));
		
		objGeom.setTextureCoordinate(0,new Point2f(0.5f,1.05f));
		objGeom.setTextureCoordinate(1,new Point2f(1f,1f));
		objGeom.setTextureCoordinate(2,new Point2f(0f,1f));
		objGeom.setTextureCoordinate(3,new Point2f(1f,0f));
		objGeom.setTextureCoordinate(4,new Point2f(0f,0f));
		
		objGeom.setTextureCoordinate(5,new Point2f(0.5f,1.05f));
		objGeom.setTextureCoordinate(6,new Point2f(1f,1f));
		objGeom.setTextureCoordinate(7,new Point2f(0f,1f));
		objGeom.setTextureCoordinate(8,new Point2f(1f,0f));
		objGeom.setTextureCoordinate(9,new Point2f(0f,0f));
		
		objGeom.setTextureCoordinate(10,new Point2f(0.5f,1.05f));
		objGeom.setTextureCoordinate(11,new Point2f(1f,1f));
		objGeom.setTextureCoordinate(12,new Point2f(0f,1f));
		objGeom.setTextureCoordinate(13,new Point2f(1f,0f));
		objGeom.setTextureCoordinate(14,new Point2f(0f,0f));
		
		objGeom.setTextureCoordinate(15,new Point2f(0.5f,1.05f));
		objGeom.setTextureCoordinate(16,new Point2f(1f,1f));
		objGeom.setTextureCoordinate(17,new Point2f(0f,1f));
		objGeom.setTextureCoordinate(18,new Point2f(1f,0f));
		objGeom.setTextureCoordinate(19,new Point2f(0f,0f));
				
		for (int i=0;i<20;i++) objGeom.setColor(i,new Color3f(1f,0f,0f));
		
		return objGeom;
	}
	
	Appearance createAppearance(String str)
	{
        	PolygonAttributes polyAttrib = new PolygonAttributes();
            	polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
            	if (str.equals("line")) polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
            	if (str.equals("point")) polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_POINT);
            	Appearance polyAppear = new Appearance();
            	polyAppear.setPolygonAttributes(polyAttrib);

            	return polyAppear;
        }
        
        
        Background mkBackground(String textureName)
        {
        	TextureLoader loader=new TextureLoader(textureName,this);
        	ImageComponent2D image=loader.getImage();
        	Background background=new Background(image);
        	background.setApplicationBounds(new BoundingSphere(new Point3d(0,0,0),150d));
        	return background;
        }
        
        Appearance createTextureApp(String textureName)
	{
		Appearance app=new Appearance();
	        TextureLoader loader=new TextureLoader(textureName,this);
        	ImageComponent2D image=loader.getImage();
        	
        	Texture2D texture=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
        	texture.setImage(0, image);
        	texture.setEnable(true);
        	texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        	texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        	        	
        	app.setTexture(texture);
        	app.setTextureAttributes(new TextureAttributes());
        	
        	PolygonAttributes polyAttrib = new PolygonAttributes();
            	polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
            	app.setPolygonAttributes(polyAttrib);
        	
        	return app;
    	}
        
		
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	
	public void windowClosing(WindowEvent e)
	{
		System.exit(1);
	}
	
	public static void main(String args[])
	{
		Tuto2TextureFormeElementaire myApp=new Tuto2TextureFormeElementaire();
		myApp.setSize(500,300);
		myApp.setVisible(true);
	}
}

