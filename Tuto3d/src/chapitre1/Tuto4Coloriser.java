package chapitre1;
/*
ecrit par:Roswell
email:philgauthier_@hotmail.com

Maintenant que l'on sait manipuler des objets, nous allons d�couvrir 
les diff�rentes primitives que nous offre le java3D ainsi que le contr�le 
basic de leurs apparences. Les apparences correspondent a la classe Appareance, 
pour d�finir une couleur nous devons utilser la classe ColoringAttributes, 
sp�cifier sa couleur et son mode de rendu (FASTEST, NICEST, SHADE_FLAT,ou SHADE_GOURAUD ).
Dans les primitives la liaison (par r�f�rence ) entre g�ometrie et apparence 
est faite dans le constructeur, vous n'aurez donc pas encore �; vous en soucier. 
En effet, jusqu'a pr�sent nous utilisions la classe ColorCube() qui avait d�ja une apparence 
pr�configur�e.
 */

// classes Java standard
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
import java.awt.Font;
// classes Java 3D
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.Appearance;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;

public class Tuto4Coloriser extends Frame implements WindowListener
{	    
	public Tuto4Coloriser() {
		super("- 4 primitives aux apparences simples -");
		this.addWindowListener(this);
		setLayout(new BorderLayout());

		// 1ere �tape cr�ation du Canvas3d qui vas afficher votre univers virtuel avec une config pr�d�finie
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		this.add("Center", canvas3D);

		// 2eme �tape on cr�e notre scene (regroupement d'objet)
		BranchGroup branchGroup = createSceneGraph();
		// on les compile pour optimiser les calcules
		branchGroup.compile();

		// 3.1 �tape on cr�e l'univers SimpleUniverse qui va contenir notre scene 3d (canvas)
		SimpleUniverse simpleUniverse = new SimpleUniverse(canvas3D);
		// 3.2 on met le plan de projection en arriere par rapport a l'origine
		simpleUniverse.getViewingPlatform().setNominalViewingTransform();
		// 3.3 on place la scene dans l'univers simpleU
		simpleUniverse.addBranchGraph(branchGroup);
	}	

	//cr�e un regroupement d'objet contenant un objet cube
	public BranchGroup createSceneGraph() {
		//on cr�e le Bg principal
		BranchGroup rootBranchGroup=new BranchGroup();

		//------------ debut creation des apparences -----------------
		// on cr�e une apparence de couleur bleue
		Appearance blueAppearance = new Appearance();
		ColoringAttributes blueColoringAttribute=new ColoringAttributes();
		blueColoringAttribute.setColor(0.1f,0.1f,1.0f);
		// on precise le rendu
		blueColoringAttribute.setShadeModel(ColoringAttributes.NICEST);
		blueAppearance.setColoringAttributes(blueColoringAttribute);
		// on cr�e une apparence de couleur roug
		Appearance redAppearance = new Appearance();
		ColoringAttributes redColoringAttribute=new ColoringAttributes();
		redColoringAttribute.setColor(1.0f,0.1f,0.1f);
		// on precise le rendu
		redColoringAttribute.setShadeModel(ColoringAttributes.NICEST);
		redAppearance.setColoringAttributes(redColoringAttribute);	
		// on cr�e une apparence de couleur vert
		Appearance greenAppearance = new Appearance();
		ColoringAttributes greenColoringAttribute=new ColoringAttributes();
		greenColoringAttribute.setColor(0.1f,1.0f,0.2f);
		// on precise le rendu
		greenColoringAttribute.setShadeModel(ColoringAttributes.NICEST);
		greenAppearance.setColoringAttributes(greenColoringAttribute);	
		// on cr�e une apparence de couleur orange
		Appearance orangeAppearance = new Appearance();
		ColoringAttributes orangeColoringAttribute=new ColoringAttributes();
		orangeColoringAttribute.setColor(0.8f,0.4f,0.2f);
		// on precise le rendu
		orangeColoringAttribute.setShadeModel(ColoringAttributes.NICEST);
		orangeAppearance.setColoringAttributes(orangeColoringAttribute);		
		//------------ fin creation des apparences -----------------

		//------------ d�but de creation de la sphere ------------
		// on cr�e un vecteur de translation 30 cm suivant les Y (dans l'autre sens)
		Transform3D translate1Transform3D = new Transform3D();
		translate1Transform3D.set(new Vector3f(0.4f, 0.4f, 0.0f));
		// on cr�e un groupe de transformation TG1 suivant la matrice de transformation translate1
		TransformGroup sphereTransformGroup = new TransformGroup(translate1Transform3D);
		// on cr�e un cube qui herite de cette rotation
		sphereTransformGroup.addChild(new Sphere(0.3f,blueAppearance));// de rayon 20 cm peinte en bleu
		//------------ fin de creation de la sphere --------------	

		//------------ d�but de creation du cone ------------
		// on cr�e un vecteur de translation 30 cm suivant les Y (dans l'autre sens)
		Transform3D translate2Transform3D = new Transform3D();
		translate2Transform3D.set(new Vector3f(-0.4f, -0.4f, 0.0f));
		// on cr�e un groupe de transformation TG2 suivant la matrice de transformation translate2
		TransformGroup coneTransformGroup = new TransformGroup(translate2Transform3D);			
		// on cr�e un cone qui herite de cette translation
		coneTransformGroup.addChild(new Cone(0.2f, 0.4f,Cone.ENABLE_APPEARANCE_MODIFY,redAppearance));// de rayon 20 cm de hauteur 40cm peint en rouge
		//------------ fin de creation du cone --------------	

		//------------ d�but de creation d'une boite ------------
		// on cr�e un vecteur de translation 30 cm suivant les Y (dans l'autre sens)
		Transform3D translate3Transform3D = new Transform3D();
		translate3Transform3D.set(new Vector3f(-0.4f, 0.4f, 0.0f));
		// on cr�e une matrice de tranformation pour faire tourner notre cube
		Transform3D rotateTransform3D = new Transform3D();
		rotateTransform3D.rotZ(2*Math.PI/3.0d);//rotation d'angle 2Pi/3
		// on combine les deux transformations: translation puis rotation
		translate3Transform3D.mul(rotateTransform3D);
		// on cr�e un groupe de transformation TG3 suivant la matrice de transformation translate3
		TransformGroup boxTransformGroup = new TransformGroup(translate3Transform3D);			
		// on cr�e un cube qui herite de cette rotation
		boxTransformGroup.addChild(new Box(0.2f, 0.4f,0.1f,orangeAppearance));// de largeur 40 cm, de hauteur 80cm, de profondeur 20 cm peint en vert
		//------------ fin de creation d'une boite --------------	

		//------------ d�but de creation d'un texte en 3d------------
		// on definit la font 3d dont on va se servir, attention les font sont surdimentionn� 
		Font3D font3D=new Font3D(new Font("Helvetica",Font.PLAIN,1),new FontExtrusion());
		// on utilise cette font3D pour cr�er un texte en 3d
		Text3D text3D=new Text3D(font3D,new String("Coucou"),new Point3f(3.0f,-3.0f,0.0f));
		text3D.setAlignment(Text3D.ALIGN_CENTER);
		// finalement on transforme ce txt en shape3d avec l'apparence app_vert affichable par java3d
		//Shape3D text=new Shape3D(textGeom,app_vert); ou bien
		Shape3D shape3D=new Shape3D();
		shape3D.setGeometry(text3D);
		shape3D.setAppearance(greenAppearance);
		// on r�duit la taille du texte car beaucoup trop grande par d�faut
		Transform3D scaleTransform3D = new Transform3D();
		scaleTransform3D.setScale(0.2f);
		TransformGroup shapeTransformGroup = new TransformGroup(scaleTransform3D);
		shapeTransformGroup.addChild(shape3D);
		//------------ fin de creation d'un texte en 3d--------------	

		rootBranchGroup.addChild(sphereTransformGroup);
		rootBranchGroup.addChild(coneTransformGroup);
		rootBranchGroup.addChild(boxTransformGroup);
		rootBranchGroup.addChild(shapeTransformGroup);

		// met le fond en blanc
		Background background = new Background(0.2f, 0.2f, 0.2f);
		background.setApplicationBounds(new BoundingSphere());
		rootBranchGroup.addChild(background);

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
		Tuto4Coloriser myApp=new Tuto4Coloriser();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}

}