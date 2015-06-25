package chapitre3;
/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Nous allons maintenant voir un exemple de mouvement plus complexe qui sera en 
  fait compos� de 3 TG, l'un associ� � un interpolator de 
  type "PositionPathInterpolator", et l'autre � un "RotationInterpolator", 
  le dernier servant � d�finir un rayon de rotation pour notre cube. Le 
  mouvement sera compos� d'une rotation globale appliqu�e � 
  un vecteur servant de rayon, enfin un mouvement d'oscillation sera associ� 
  au cube. Le plus simple pour comprendre est de regarder le sch�ma d'organisation 
  g�n�rale. 
  
*/

//Java standard API
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PositionPathInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Tuto4MouvementAnimation extends Frame implements WindowListener
{	    
	public Tuto4MouvementAnimation()
	{
        super("- mouvement de rotation sinuso�dale -");
        this.addWindowListener(this);
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        BranchGroup scene = createSceneGraph();
        scene.compile();
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);
    }
	
	// crée un regroupement d'objets contenant un objet cube qui va décrire un cercle sur le 
	// plan izo-y et un mouvement oscillatoire sur le plan izo-teta dans le repere cylindrique
	// pour ceux qui connaissent
	public BranchGroup createSceneGraph()
	{
		//on crée le Bg principal
		BranchGroup objRoot=new BranchGroup();
		
		// -----------début de création de la rotation-------------------------
			TransformGroup objSpin=new TransformGroup();
		
			// permet de modifier l'objet pendant l'execution
			objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
			// on crée un fonction de rotation au cours du temps
			Alpha rotationAlpha=new Alpha(-1,4000);
		
			// on crée un comportement qui va appliquer la rotation à l'objet voulu
			RotationInterpolator rotator=new RotationInterpolator(rotationAlpha,objSpin);
		
			// on définit la zone sur laquelle va s'appliquer la rotation
			BoundingSphere bounds=new BoundingSphere();
			rotator.setSchedulingBounds(bounds);
			objSpin.addChild(rotator);
			
			objRoot.addChild(objSpin);		
		// -----------fin de création de la rotation---------------------------
		
		// -----------début de création du rayon-------------------------------
			Transform3D rayonT=new Transform3D();
			rayonT.set(new Vector3f(0.5f, 0.0f, 0.0f));
			TransformGroup rayon=new TransformGroup(rayonT);
			objSpin.addChild(rayon);
		// -----------fin de création du rayon---------------------------------
		
		// -----------début de création d'oscillation verticale----------------
			Transform3D oscilT=new Transform3D();
			TransformGroup oscil=new TransformGroup();

			// permet de modifier l'objet pendant l'execution
			oscil.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

			// on crée un fonction de rotation au cours du temps
			Alpha transAlpha=new Alpha(-1,2000);

			// on crée notre chemin: une matrice de 3 Point3f: point de départ, point d'arrivée, 
			// puis retour au point de départ pour permettre de boucler
			Point3f[] chemin=new Point3f[3];
			chemin[0]=new Point3f(0.0f,0.05f,0.0f);
			chemin[1]=new Point3f(0.0f,-0.05f,0.0f);
			chemin[2]=new Point3f(0.0f,0.05f,0.0f);

			// on crée une matrice de float qui sert a faire correspondre 
			// à chaque point dans l'espace un point dans l'échelle du temps qui s'étend de 0 à 1
			float[] timePosition={0.0f,0.5f,1.0f};

			PositionPathInterpolator interpol=new PositionPathInterpolator(transAlpha,oscil,oscilT,timePosition,chemin);
			
			// on définit la zone sur laquelle va s'appliquer le chemin
			BoundingSphere bounds2=new BoundingSphere();
			interpol.setSchedulingBounds(bounds2);
			oscil.addChild( interpol);
		
			rayon.addChild(oscil);
		// -----------fin de cr�ation d'oscillation verticale------------------
		
		// on cr�e un cube
		oscil.addChild(new ColorCube(0.2));// de rayon 50 cm

		return objRoot;
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
		Tuto4MouvementAnimation myApp=new Tuto4MouvementAnimation();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
	
}