package chapitre1;
/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Nous allons maintenant voir comment, � l'aide des TransformGroup, 
on peut positionner deux objets l'un par rapport � l'autre. Nous d�taillons 
ici les 3 utilisations principale de la classe Transform3D qui permet de faire 
des translations, des rotations ( voir le sch�ma rep�re ), et 
de retailler vos objet. Faites attention, une instance d'un objet ( Shape3D )
 ne peut pas �tre ref�renc�e par deux TransformGroup 
ou BranchGroup. Par contre plusieurs Shape3D peuvent tres bien avoir le m�me 
BG ou TG. Il faut cr�er une instance de la Shape3D pour chaque TransformGroup 
ou BranchGroup.
*/

//Java standart API
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Tuto2Cube3dTG extends Frame implements WindowListener
{	    
	public Tuto2Cube3dTG() {
        super("- un TG pour orienter le cube -");
        this.addWindowListener(this);
        setLayout(new BorderLayout());
        
        // 1ere étape création du Canvas3d qui vas afficher votre univers virtuel avec une config prédéfinie
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        this.add("Center", canvas3D);
        
        // 2eme étape on crée notre scene (regroupement d'objet)
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
	
	//crée un regroupement d'objet contenant un objet cube
	public BranchGroup createSceneGraph() {
		//on crée le Bg principal
		BranchGroup rootBranchGroup = new BranchGroup();
		
		// on crée une matrice de tranformation pour faire tourner notre cube
		// les rotation s'effectues en prenant l'axe choisit comme axe de rotation dans le sens trigonometrique
		Transform3D rotateTransform3D = new Transform3D();
		rotateTransform3D.rotX(Math.PI/3.0d);//rotation d'angle Pi/3
		
        // on crée un groupe de transformation utilisant la matrice de transformation
        TransformGroup cubeTransformGroup = new TransformGroup(rotateTransform3D);
       
		// on crée un cube qui herite de cette rotation
		cubeTransformGroup.addChild(new ColorCube(0.5));// de rayon 50 cm
		
		//on ajout notre objet objRotate a notre groupe d'objet ( qui ne contiends qu'un cube dans notre cas ) 
		rootBranchGroup.addChild(cubeTransformGroup);
		
		return rootBranchGroup;
	}
    	
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	
	public void windowClosing(WindowEvent e) { System.exit(1); }
    	
	public static void main(String args[]) {
		Tuto2Cube3dTG myApp=new Tuto2Cube3dTG();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
	
}