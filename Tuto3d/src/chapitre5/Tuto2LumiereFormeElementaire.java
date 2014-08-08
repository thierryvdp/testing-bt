package chapitre5;

/* Chapitre 5: Section 2, lumières sur des formes élementaires texturées ou non
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
import com.sun.j3d.utils.image.TextureLoader;
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
import javax.media.j3d.PointLight;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Material;
import javax.vecmath.Point3f;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;

class Tuto2LumiereFormeElementaire extends Frame implements WindowListener
{
	Tuto2LumiereFormeElementaire()
	{
		super("-Chapitre 5: lumières sur formes élementaires -");
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

	BranchGroup createScene1()	{
		BranchGroup scene=new BranchGroup();

		//creation des 4 geometries
		Shape3D obj2=new Shape3D(createBarriere(),createAppearance(""));
		Shape3D obj3=new Shape3D(createBarriere(),createTextureApp("oak.jpg"));

		//mise en place des objets
		TransformGroup tr2=mkTranslation(new Vector3f(-0.6f,0.63f,0f));
		TransformGroup tr3=mkTranslation(new Vector3f(0.6f,0.63f,0f));

		TransformGroup objSpin2=mkSpin();
		TransformGroup objSpin3=mkSpin();

		objSpin2.addChild(obj2);
		tr2.addChild(objSpin2);
		objSpin3.addChild(obj3);
		tr3.addChild(objSpin3);

		scene.addChild(tr2);
		scene.addChild(tr3);

		//lumières
		PointLight pointLi=new PointLight(new Color3f(1f,1f,1f),new Point3f(0f,0f,0.5f),new Point3f(1f,0f,0f));
		pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
		scene.addChild(pointLi);

		AmbientLight ambLi=new AmbientLight(new Color3f(1f,1f,1f));
		ambLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
		scene.addChild(ambLi);	

		scene.compile();
		return scene;
	}

	TransformGroup mkTranslation(Vector3f vect) {
		Transform3D t3d=new Transform3D();
		t3d.setTranslation(vect);
		t3d.setScale(1.8f);
		return new TransformGroup(t3d);
	}

	TransformGroup mkSpin() {
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
	Geometry createBarriere() {

		int tab[]=new int[4];
		tab[0]=5;
		tab[1]=5;
		tab[2]=5;
		tab[3]=5;

		TriangleStripArray objGeom=new TriangleStripArray(20,
				TriangleStripArray.COORDINATES|
				TriangleStripArray.COLOR_3|
				TriangleStripArray.TEXTURE_COORDINATE_2|
				TriangleStripArray.NORMALS,
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


		//vecteurs normaux pour face 1
		Vector3f vect0=new Vector3f();
		Point3f point0=new Point3f();
		Point3f point2=new Point3f();
		Point3f point1=new Point3f();
		objGeom.getCoordinate(0,point0);
		objGeom.getCoordinate(2,point2);
		objGeom.getCoordinate(1,point1);
		vect0.cross(new Vector3f(point2.x-point0.x,point2.y-point0.y,point2.z-point0.z),new Vector3f(point1.x-point0.x,point1.y-point0.y,point1.z-point0.z));
		vect0.normalize();
		objGeom.setNormal(0,vect0);
		for (int i=1;i<5;i++) objGeom.setNormal(i,new Vector3f(0f,0f,1f));

		//vecteurs normaux pour face 2, 3 et 4
		for (int i=6;i<10;i++) objGeom.setNormal(i,new Vector3f(0f,0f,-1f));
		for (int i=10;i<15;i++) objGeom.setNormal(i,new Vector3f(-1f,0f,0f));
		for (int i=15;i<20;i++) objGeom.setNormal(i,new Vector3f(1f,0f,0f));

		//couleurs de l'objet
		for (int i=0;i<20;i++) objGeom.setColor(i,new Color3f(0.63f,0.42f,0.21f));

		return objGeom;
	}

	public Appearance createAppearance(String str)	{
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		if (str.equals("line")) polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		if (str.equals("point")) polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_POINT);
		Appearance polyAppear = new Appearance();
		polyAppear.setPolygonAttributes(polyAttrib);
		polyAppear.setMaterial(new Material(new Color3f(0.63f,0.42f,0.21f),new Color3f(0f,0f,0f),new Color3f(0.63f,0.42f,0.21f),new Color3f(0.63f,0.42f,0.21f),64));
		return polyAppear;
	}

	public Appearance createTextureApp(String textureName)	{
		Appearance app=new Appearance();
		TextureLoader loader=new TextureLoader(textureName,this);
		ImageComponent2D image=loader.getImage();

		Texture2D texture=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);

		app.setTexture(texture);
		TextureAttributes textAt=new TextureAttributes();
		textAt.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(textAt);

		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		app.setPolygonAttributes(polyAttrib);

		app.setMaterial(new Material(new Color3f(0.63f,0.42f,0.21f),new Color3f(0f,0f,0f),new Color3f(0.63f,0.42f,0.21f),new Color3f(0.63f,0.42f,0.21f),64));

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
		Tuto2LumiereFormeElementaire myApp=new Tuto2LumiereFormeElementaire();
		myApp.setSize(350,350);
		myApp.setVisible(true);
	}
}

