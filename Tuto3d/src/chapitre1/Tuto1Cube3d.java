package chapitre1;
/**
ecrit par:Roswell
email:philgauthier_@hotmail.com

Tout d'abord, nous allons étudier la structure minimale d'un programme 
en java 3D, ce qui permettra de mettre en application le modèle général 
vu plus haut (dans le tutorial) 
La premiere classe utilisé est Canvas3D qui est dérive de Canvas 
( donc même utilisation ) qui nous sert juste à afficher du java3D. 

Les classes utilisés sont 
SimpleUniverse :  qui représente votre univers 3d qui va contenir tout votre scene. 
                  C'est en fait un univers qui a ses parametre préconfiguré pour simplifier le code. 
                  Nous n'avons jamais vue d'autre exemple d'univers dans le tutorial de sun aussi nous n'utiliserons 
                  que celui-ci. Nous ne configurons que la camera. Notre univers contiendra 
                  un seul regroupement d'objet qui correspond à la classe BranchGroup.
                  
BranchGroup (BG): ce BG ne contiendra qu'un seul objet ( classe Shape3D) qui est la classe ColorCube

ColorCube       : a une géométrie et une apparence prédéfinie.

Pour associer notre objet ( Shape3D ), il faut créer un lien d'héritage 
entre le BG et la Shape3D, ce lien se fait grace à la méthode 
addChild()

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
import com.sun.j3d.utils.geometry.ColorCube; 


public class Tuto1Cube3d extends Frame implements WindowListener {	    

	public Tuto1Cube3d() {
        super("- un cube vue de face -");
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
		BranchGroup rootBranchGroup=new BranchGroup();	
		// on creer un cube
		rootBranchGroup.addChild(new ColorCube(0.5));// de rayon 50 cm
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
		Tuto1Cube3d myApp=new Tuto1Cube3d();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
	
}