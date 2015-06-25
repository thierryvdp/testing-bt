package chapitre2;
/* Chapitre 2: Section 1, cr�ation de geometries elementaires
 * Auteur    : daboul 
 * Date      : 03/00
 */

//Java standart API
import graphik.tool.BehaviorMove;
import graphik.tool.D8Factory;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

class MonTutoPyramide extends Frame implements WindowListener
{

	MonTutoPyramide()
	{
		super("- Chapitre 2 : geometries élementaires -");
		addWindowListener(this);
		setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center",canvas);
		BranchGroup myScene=createScene();
		myScene.compile();
		SimpleUniverse myWorld=new SimpleUniverse(canvas);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		myWorld.addBranchGraph(myScene);
	}
	
	BranchGroup createScene() {
		BranchGroup scene=new BranchGroup();
//		Shape3D triangle=new Shape3D(triangle(),mkFillApp());
		Shape3D triangle=new Shape3D(D8Factory.getInstance().getNewD8(10f),mkFillApp());
		
		/** ROTATION **/		
//		TransformGroup mouvementTransformGroup = getRotater(4000);
//		mouvementTransformGroup.addChild(triangle); // set notre triangle au TG de rotation

		/** CHEMINEMENT **/
//		TransformGroup mouvementTransformGroup = getPather(6000);
//		mouvementTransformGroup.addChild(triangle); // set notre triangle au TG de rotation

		/** ECHELLE **/
//		TransformGroup mouvementTransformGroup = getScaler(6000,0.5f,1.2f);
//		mouvementTransformGroup.addChild(triangle); // set notre triangle au TG de rotation

//		/** ROTATION **/
//		TransformGroup mouvementRotation = getRotater(4000);
//		scene.addChild(mouvementRotation);
//		/** RAYON **/
//		Transform3D rayonT=new Transform3D();
//		rayonT.set(new Vector3f(0.5f, 0.0f, 0.0f));
//		TransformGroup mouvementRayon=new TransformGroup(rayonT);
//		mouvementRotation.addChild(mouvementRayon);
//		/**OSCILLE**/
//		TransformGroup mouvementOscille = getPather(2000);
//		mouvementRayon.addChild(mouvementOscille);
//		mouvementOscille.addChild(triangle);
		
       	TransformGroup TG=new TransformGroup();
    	TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    	TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);         
    	TG.addChild(triangle);

		
    	BehaviorMove behav=new BehaviorMove(TG);
		behav.setSchedulingBounds(new BoundingSphere());
		TG.addChild(behav);
		scene.addChild(TG);
		
    	//lumieres
    	PointLight pointLi=new PointLight(new Color3f(1f,1f,1f),new Point3f(0f,0f,0.5f),new Point3f(1f,0f,0f));
    	pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
    	scene.addChild(pointLi);
    	
    	AmbientLight ambLi=new AmbientLight(new Color3f(1f,1f,1f));
    	ambLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
    	scene.addChild(ambLi);	

		
		return scene;
	}
	
//	private TransformGroup getPather(long speedMilli) {
//		TransformGroup pathTransformGroup=new TransformGroup();
//		pathTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//		Alpha cadenceAlpha=new Alpha(-1,speedMilli);               // le mouvement est infini et se r�p�te toutes les 4 secondes
//		Point3f[] chemin=new Point3f[3];        // itin�raire
//		chemin[0]=new Point3f(0.8f,0.0f,0.0f);  // itin�raire point 1
//		chemin[1]=new Point3f(-0.8f,0.0f,0.0f); // itin�raire point 2
//		chemin[2]=new Point3f(0.8f,0.0f,0.0f);  // itin�raire point 3 on revient au depart ...
//		float[] timePosition={0.0f,0.50f,1.0f}; // timing pour atteindre chaque point de l'itin�raire
//
//		Transform3D trans=new Transform3D(); // necessaire � l'interpolateur
//		PositionPathInterpolator interpol=new PositionPathInterpolator(cadenceAlpha,pathTransformGroup,trans,timePosition,chemin); // interpol de cheminement
//
//		BoundingSphere effectAre=new BoundingSphere(); // creation de l'aire d'effet
//		interpol.setSchedulingBounds(effectAre); // set l'aire d'effet � l'interpolateur de rotation
//		pathTransformGroup.addChild(interpol); // set l'interpolateur au TG
//		return pathTransformGroup;
//	}

//	private TransformGroup getScaler(long speedMilli,float startSize,float stopSize) {
//		TransformGroup scaleTransformGroup=new TransformGroup();
//		scaleTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//		Alpha cadenceAlpha=new Alpha(-1,speedMilli);               // le mouvement est infini et se r�p�te toutes les 4 secondes
//
//		Transform3D trans=new Transform3D(); // necessaire � l'interpolateur
//		ScaleInterpolator interpol=new ScaleInterpolator(cadenceAlpha,scaleTransformGroup, trans,startSize,stopSize);
//		
//		BoundingSphere effectAre=new BoundingSphere(); // creation de l'aire d'effet
//		interpol.setSchedulingBounds(effectAre); // set l'aire d'effet � l'interpolateur de rotation
//		scaleTransformGroup.addChild(interpol); // set l'interpolateur au TG
//		return scaleTransformGroup;
//	}

	
//	private TransformGroup getRotater(long speedMilli) {
//		TransformGroup spinTransformGroup=new TransformGroup();
//		spinTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//		Alpha cadenceAlpha=new Alpha(-1,speedMilli);               // le mouvement est infini et se r�p�te toutes les 4 secondes
//		RotationInterpolator interpol=new RotationInterpolator(cadenceAlpha,spinTransformGroup); // mouvement de rotation � la cadenceAlpha sur le TG
//		BoundingSphere effectAre=new BoundingSphere(); // creation de l'aire d'effet
//		interpol.setSchedulingBounds(effectAre); // set l'aire d'effet � l'interpolateur de rotation
//		spinTransformGroup.addChild(interpol); // set l'interpolateur au TG
//		return spinTransformGroup;
//	}
	
	
//	private Geometry triangle() {
//		float tan30=(float) Math.tan(Math.PI/6);
////		float tan60=(float) Math.tan(Math.PI/3);
//		float sin60=(float) Math.sin(Math.PI/3);
//
//		float x=0.5f;
//		float y1=tan30/2;
//		float y2=sin60;
//		float a=(float) Math.sqrt(1-(x*x));
//		float z=(float) Math.sqrt((a*a)-(y1*y1));
////		Point3f sommet1=new Point3f(x, y2, 0);
////		Point3f sommet2=new Point3f(0, 0, 0);
////		Point3f sommet4=new Point3f(1, 0, 0);
////		Point3f sommet3=new Point3f(x, y1, z);
////		Point3f sommet6=new Point3f(x, y1, z*(-1));
//
//		Point3f sommet1=new Point3f(0, y2-y1, 0);
//		Point3f sommet2=new Point3f(-x, -y1, 0);
//		Point3f sommet4=new Point3f(1-x, -y1, 0);
//		Point3f sommet3=new Point3f(0, 0, z);
//		Point3f sommet6=new Point3f(0, 0, z*(-1));
//
//		int tab[]=new int[1];
//		tab[0]=8;
//		TriangleStripArray triangleStrip=new TriangleStripArray(8,TriangleStripArray.COORDINATES|TriangleStripArray.COLOR_3,tab);
//		triangleStrip.setCoordinate(0,sommet1);
//		triangleStrip.setCoordinate(1,sommet2);
//		triangleStrip.setCoordinate(2,sommet3);
//		triangleStrip.setCoordinate(3,sommet4);
//		triangleStrip.setCoordinate(4,sommet1);
//		triangleStrip.setCoordinate(5,sommet6);
//		triangleStrip.setCoordinate(6,sommet2);
//		triangleStrip.setCoordinate(7,sommet4);
//		triangleStrip.setColor(0,new Color3f(1f,0f,0f));
//		triangleStrip.setColor(1,new Color3f(0f,0f,1f));
//		triangleStrip.setColor(2,new Color3f(0f,1f,0f));
//		triangleStrip.setColor(3,new Color3f(1f,0f,1f));
//		triangleStrip.setColor(4,new Color3f(1f,1f,0f));
//		triangleStrip.setColor(4,new Color3f(0f,1f,1f));
//		triangleStrip.setColor(6,new Color3f(0f,0f,0f));
//		triangleStrip.setColor(7,new Color3f(1f,1f,1f));
//		
//		for(int i=0;i<8;i++) triangleStrip.setColor(i,new Color3f(1f,0f,1f));
//
//		return triangleStrip;
//	}
	
	public Appearance mkFillApp() {
		Appearance app=new Appearance();
		PolygonAttributes polyAtt=new PolygonAttributes(PolygonAttributes.POLYGON_FILL,PolygonAttributes.CULL_NONE,0f);
		app.setPolygonAttributes(polyAtt);
		
		//material -> uniquement si il y a des lumi�res
		Material mat=new Material(new Color3f(1f,1f,0f),new Color3f(0f,0f,0f),new Color3f(1f,1f,0f),new Color3f(1f,1f,1f),64);
		app.setMaterial(mat);
		
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
		MonTutoPyramide myApp=new MonTutoPyramide();
		myApp.setSize(400,400);
		myApp.setVisible(true);
	}
}

