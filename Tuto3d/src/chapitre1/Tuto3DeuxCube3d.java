package chapitre1;
/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Nous allons maintenant voir comment, à l'aide des TransformGroup, 
on peut positionner deux objets l'un par rapport à l'autre. Nous détaillons 
ici les 3 utilisations principale de la classe Transform3D qui permet de faire 
des translations, des rotations ( voir le schéma repère ), et 
de retailler vos objet. Faites attention, une instance d'un objet ( Shape3D ) 
ne peut pas être reférencée par deux TransformGroup 
ou BranchGroup. Par contre plusieurs Shape3D peuvent tres bien avoir le même 
BG ou TG. Il faut créer une instance de la Shape3D pour chaque TransformGroup 
ou BranchGroup.
 */

// classes Java standart
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
// classes Java 3D
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.geometry.ColorCube;

// attention vous ne pouvez pas utiliser un même objet (shape3d), comme notre cube, dans plusieur Group de transformation
// il faut créer 2 entité distinct comme nous l'avons fait

public class Tuto3DeuxCube3d extends Frame implements WindowListener
{	    
	public Tuto3DeuxCube3d() {
		super("- 2 TG pour placer 2 cubes -");
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

		//------------ début de creation du premier cube  ------------
		// on crée un vecteur de translation 30 cm suivant les Y
		Transform3D translate1Transform3D = new Transform3D();
		translate1Transform3D.set(new Vector3f(0.4f, 0.4f, 0.0f));
		// on crée une matrice de tranformation pour faire tourner notre cube d'angle PI/3
		Transform3D rotate1Transform3D = new Transform3D();
		rotate1Transform3D.rotX(Math.PI/3.0d);//rotation d'angle Pi/3
		// on combine les deux transformations: translation puis rotation
		translate1Transform3D.mul(rotate1Transform3D);
		// on crée un groupe de transformation rotate suivant la matrice de transformation translate1
		TransformGroup cube1TransformGroup = new TransformGroup(translate1Transform3D);
		// on crée un cube qui herite de cette rotation
		cube1TransformGroup.addChild(new ColorCube(0.3));// de rayon 30 cm
		//------------ fin de creation du premier cube  ------------


		//------------ début de creation du deuxieme cube  ------------
		// on crée un vecteur de translation 30 cm suivant les Y (dans l'autre sens)
		Transform3D translate2Transform3D = new Transform3D();
		translate2Transform3D.set(new Vector3f(-0.4f, -0.4f, 0.0f));
		// on crée une matrice de tranformation pour faire tourner notre cube
		Transform3D rotate2Transform3D = new Transform3D();
		rotate2Transform3D.rotZ(Math.PI/3.0d);//rotation d'angle Pi/3
		// on combine les deux transformations: translation puis rotation
		translate2Transform3D.mul(rotate2Transform3D);
		// on réduit la taille du cube par 2 (on la multiplie par 0.5)
		translate2Transform3D.setScale(0.5f);
		// on crée un groupe de transformation rotate suivant la matrice de transformation translate1
		TransformGroup cube2TransformGroup = new TransformGroup(translate2Transform3D);
		// on crée un cube qui herite de cette rotation
		cube2TransformGroup.addChild(new ColorCube(0.3));// de rayon 20 cm
		//------------ fin de creation du deuxieme cube  ------------		

		rootBranchGroup.addChild(cube1TransformGroup);
		rootBranchGroup.addChild(cube2TransformGroup);

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
		Tuto3DeuxCube3d myApp=new Tuto3DeuxCube3d();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}

}