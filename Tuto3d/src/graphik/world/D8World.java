package graphik.world;
/*
 * Chapitre 2: Section 1, cr�ation de geometries elementaires
 * Auteur : daboul
 * Date : 03/00
 */

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

// Java standart API
import graphik.tool.BehaviorCamera;
import graphik.tool.BehaviorMove;
import graphik.tool.D8Factory;

class D8World extends Frame implements WindowListener {

	D8World() {
		super("D8 world");
		addWindowListener(this);
		setLayout(new BorderLayout());

		//creation de la scene java3d
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center", canvas);

		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();

		BranchGroup myScene = createScene(universe);
		myScene.compile();
		universe.addBranchGraph(myScene);
	}

	BranchGroup createScene(SimpleUniverse universe) {
		BranchGroup scene = new BranchGroup();
		Shape3D d8Shape = new Shape3D(D8Factory.getInstance().getNewD8(4f), mkFillApp());

		TransformGroup d8TG = new TransformGroup();
		d8TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		d8TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		d8TG.addChild(d8Shape);
		scene.addChild(d8TG);

		//pilotage d8
		BehaviorMove controle = new BehaviorMove(d8TG);
		controle.setSchedulingBounds(new BoundingSphere());
		scene.addChild(controle);

		//lumieres
		PointLight pointLi = new PointLight(new Color3f(1f, 1f, 1f), new Point3f(1f, 1f, 1f), new Point3f(1f, 0f, 0f));
		pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(), 150d));
		scene.addChild(pointLi);

		AmbientLight ambLi = new AmbientLight(new Color3f(1f, 1f, 1f));
		ambLi.setInfluencingBounds(new BoundingSphere(new Point3d(), 150d));
		scene.addChild(ambLi);

		//camera
		TransformGroup view = universe.getViewingPlatform().getViewPlatformTransform();
		BehaviorCamera camera = new BehaviorCamera(view);
		camera.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
		scene.addChild(camera);

		return scene;
	}

	public Appearance mkFillApp() {
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		Appearance appearance = new Appearance();
		appearance.setPolygonAttributes(polyAttrib);

		//material -> uniquement si il y a des lumi�res
		//		Material mat=new Material(new Color3f(1f,1f,0f),new Color3f(0f,0f,0f),new Color3f(1f,1f,0f),new Color3f(1f,1f,1f),64);
		//		appearance.setMaterial(mat);
		appearance.setMaterial(new Material(new Color3f(0.63f, 0.42f, 0.21f), new Color3f(0f, 0f, 0f), new Color3f(0.63f, 0.42f, 0.21f), new Color3f(0.63f, 0.42f, 0.21f), 64));

		return appearance;
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		System.exit(1);
	}

	public static void main(String args[]) {
		D8World myApp = new D8World();
		myApp.setSize(400, 400);
		myApp.setVisible(true);
	}
}
