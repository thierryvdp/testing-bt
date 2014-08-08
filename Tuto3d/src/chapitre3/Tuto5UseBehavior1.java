package chapitre3;

/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Pour ajouter une interaction il faut donc d�finir le TG � 
      manipuler comme pouvant �tre modifiable, puis cr�er une instance 
      de votre classe behavior1 r�f�ren�ant (lien par r�f�rence) 
      ce m�me TG, lui d�finir une zone d'influence, puis  faire 
      h�riter le TG de ce behavior ( lien parental).
*/

// classes Java standard
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Tuto5UseBehavior1 extends Frame implements WindowListener
{	    
	public Tuto5UseBehavior1()
	{
        super("- interaction + rotation -");
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
	
	public BranchGroup createSceneGraph()
	{
		//on cr�e le Bg principal
		BranchGroup objRoot=new BranchGroup();
		
		//------------ debut creation des apparences ---------------
			// on cr�e une apparence de couleur orange
			Appearance app_orang = new Appearance();
			ColoringAttributes orang=new ColoringAttributes();
			orang.setColor(0.8f,0.4f,0.2f);
			orang.setShadeModel(ColoringAttributes.NICEST);
			app_orang.setColoringAttributes(orang);		
			
		//------------ fin creation des apparences -----------------

		//------------ d�but de creation d'une porte ---------------			
			// on cr�e le TG qui servira � la rotation du behavior1
        	TransformGroup TG=new TransformGroup();
        	TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        	TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);         
        		
			// on deplace la porte pour que l'axe de rotation
			// soit sur le flanc gauche de la porte et ainsi 
			// simuler son ouverture
			Transform3D rayon3D=new Transform3D();
			// on d�place la porte de 20cm � droite car elle  fait 40cm
			// et est centr�e � l'origine
			rayon3D.setTranslation(new Vector3f(0.2f,0.0f,0f));
			TransformGroup rayon = new TransformGroup(rayon3D);
			TG.addChild(rayon);
			// la porte fait 40cm de large, 80cm de haut, 10cm d'�paisseur
			rayon.addChild(new Box(0.2f, 0.4f,0.05f,app_orang));
			
			objRoot.addChild(TG);
		//------------ fin de creation d'une porte -----------------
			
		//------- d�but de ajout de l'interaction ------------------	
			Tuto5Behavior1 behav=new Tuto5Behavior1(TG);
			behav.setSchedulingBounds(new BoundingSphere());
			TG.addChild(behav);
		//------- fin de ajout de l'interaction --------------------
		
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
		Tuto5UseBehavior1 myApp=new Tuto5UseBehavior1();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
	
}