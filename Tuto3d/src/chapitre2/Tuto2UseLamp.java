package chapitre2;

/* Chapitre 2: Section 2 création d'une lampe
 * Auteur    : daboul 
 * Date      : 03/00
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
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;


class Tuto2UseLamp extends Frame implements WindowListener
{
	Tuto2UseLamp()
	{
		super("- Chapitre 2 : création de la lampe -");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld=new SimpleUniverse(canvas);
		BranchGroup myScene=createScene();
		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
				
		this.add("Center",canvas);
	}
	
	BranchGroup createScene()
	{
		BranchGroup scene=new BranchGroup();
		
		Transform3D t3d=new Transform3D();
		t3d.setScale(0.65f);
		TransformGroup tg=new TransformGroup(t3d);
		
		Tuto2Lampe lp0=new Tuto2Lampe(this,new Vector3f(-0.9f,-0.1f,0f));
		tg.addChild(lp0.createLampe("LINE","LINE","LINE"));
		
		Tuto2Lampe lp1=new Tuto2Lampe(this,new Vector3f(0f,-0.1f,0f));
		tg.addChild(lp1.createLampe("LINE","",""));
		
		Tuto2Lampe lp2=new Tuto2Lampe(this,new Vector3f(0.9f,-0.1f,0f));
		tg.addChild(lp2.createLampe("","",""));
		
		scene.addChild(tg);
		
		scene.compile();
		return scene;
	}
		
	//fonctions de gestion des evenements window
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
		Tuto2UseLamp myApp=new Tuto2UseLamp();
		myApp.setSize(500,300);
		myApp.setVisible(true);
	}
}

