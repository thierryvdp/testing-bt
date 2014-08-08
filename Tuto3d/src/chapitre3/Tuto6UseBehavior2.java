package chapitre3;

/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Maintenant que vous connaissez les bases de l'interection, nous pouvons passer 
� un exemple plus cons�quent: le contr�le complet de la rotation 
de notre porte gr�ce au pav� num�rique. 
Pour cela, il y a tr�s peu de modification � faire au fichier 
      box.java. Il suffit juste de sp�cifier que le TG que l'on va manipuler
      sera disponible non seulement en �criture, mais �galement 
      en lecture pour � tout instant pouvoir r�cuperer l'orientation 
      de la porte et la modifier.
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

public class Tuto6UseBehavior2 extends Frame implements WindowListener
{	    
	public Tuto6UseBehavior2()
	{
        super("- interaction complete: rotation -");
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
			// on cr�e le TG qui servira � la rotation du behavior2
        	TransformGroup TG=new TransformGroup();
        	TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        	TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        		
			// on deplace la porte pour que l'axe de rotation
			// soit sur le flanc gauche de la porte et ainsi 
			// simuler son ouverture
			Transform3D rayon3D=new Transform3D();
			// on d�place la porte de 20cm a droite car elle en fait 40
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
		Tuto6UseBehavior2 myApp=new Tuto6UseBehavior2();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
	
}