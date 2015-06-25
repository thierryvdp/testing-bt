package chapitre1;
/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Nous allons maintenant, pour terminer ce chapitre voir comment rendre nos 
primitives transparente. Il suffit de sp�cifier une "TransparencyAttributes" 
contenant le taux de transparence ( 0=pas de transparence et 1=invisible ) 
� notre apparence.
 */
// classes Java standart
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Tuto5Transparence extends Frame implements WindowListener
{	    	    
	public Tuto5Transparence()
	{
		super("- intro à la transparence -");
		this.addWindowListener(this);
		setLayout(new BorderLayout());

		// 1ere étape création du Canvas3d qui vas afficher votre univers virtuel avec une config prédéfinie
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		this.add("Center", canvas3D);

		// 2eme �tape on crée notre scene (regroupement d'objet)
		BranchGroup branchGroup = createSceneGraph();
		// on les compile pour optimiser les calcules
		branchGroup.compile();

		// 3.1 étape on crée l'univers SimpleUniverse qui va contenir notre scene 3d (canvas)
		SimpleUniverse simpleUniverse = new SimpleUniverse(canvas3D);
		// 3.2 on met le plan de projection en arriere par rapport a l'origine
		simpleUniverse.getViewingPlatform().setNominalViewingTransform();
		// 3.3 on place la scene dans l'univers simpleU
		simpleUniverse.addBranchGraph(branchGroup);
	}	

	//crée un regroupement d'objet contenant un objet box
	private BranchGroup createSceneGraph()
	{
		BranchGroup rootBranchGroup=new BranchGroup();	
		
		// on crée un TG pour faire tourner notre cube
		Transform3D rotate1Transform3D = new Transform3D();
		rotate1Transform3D.rotX(Math.PI/3.0d);
		Transform3D rotate2Transform3D = new Transform3D();
		rotate2Transform3D.rotY(Math.PI/3.0d);
		rotate1Transform3D.mul(rotate2Transform3D);
		// on crée un groupe de transformation rotate suivant la matrice de transformation rotate
		TransformGroup cubeTransformGroup = new TransformGroup(rotate1Transform3D);
		// on crée un cube qui herite de cette rotation
		cubeTransformGroup.addChild(new ColorCube(0.5));// de rayon 50 cm

		// on crée une apparence avec une couleur et une couche alpha 
		Appearance appearance=new Appearance();
		appearance.setColoringAttributes(new ColoringAttributes(new Color3f(0.3f,0.2f,1.0f),ColoringAttributes.SHADE_GOURAUD));
		appearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST,0.5f));  //0.5f represente 50% de transparence
		// on crée une sphere bleu de transparence 10% devant le cube
		Transform3D translateTransform3D=new Transform3D();
		translateTransform3D.set(new Vector3f(0.3f, -0.3f, 1.0f));
		TransformGroup sphereTransformGroup=new TransformGroup(translateTransform3D);
		sphereTransformGroup.addChild( new Sphere(0.3f,appearance) );

		rootBranchGroup.addChild(cubeTransformGroup);
		rootBranchGroup.addChild(sphereTransformGroup);
		return rootBranchGroup;
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
		Tuto5Transparence myApp=new Tuto5Transparence();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}

}