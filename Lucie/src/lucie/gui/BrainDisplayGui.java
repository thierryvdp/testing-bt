package lucie.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

import lucie.gui.tools3d.AppearanceTool;
import lucie.gui.tools3d.BehaviorCamera;
import lucie.gui.tools3d.BehaviorMove;
import lucie.gui.tools3d.D8Factory;
import lucie.interfaces.IBrainControler;
import lucie.interfaces.IBrainDisplay;
import lucie.interfaces.INeuronData;

public class BrainDisplayGui extends Frame implements WindowListener, IBrainDisplay {
	
	private IBrainControler brainControler;
	private BranchGroup myScene;
	private SimpleUniverse universe;

	public BrainDisplayGui(IBrainControler _brainControler) {
		super("BrainDisplay");
		brainControler=_brainControler;
		addWindowListener(this);
		setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center",canvas);
		
		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		myScene=createScene(universe);
		myScene.compile();
		universe.addBranchGraph(myScene);
	}
	
	private BranchGroup createScene(SimpleUniverse universe) {
		BranchGroup scene=new BranchGroup();
		
    	//lumieres
    	PointLight pointLi=new PointLight(new Color3f(1f,1f,1f),new Point3f(1f,1f,1f),new Point3f(1f,0f,0f));
    	pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
    	scene.addChild(pointLi);
    	
    	AmbientLight ambLi=new AmbientLight(new Color3f(1f,1f,1f));
    	ambLi.setInfluencingBounds(new BoundingSphere(new Point3d(),150d));
    	scene.addChild(ambLi);	

    	//camera
    	TransformGroup view = universe.getViewingPlatform().getViewPlatformTransform();
    	BehaviorCamera camera=new BehaviorCamera(view);
		camera.setSchedulingBounds(new BoundingSphere(new Point3d(),1000.0));
		scene.addChild(camera);

		return scene;
	}

	// ----------------------------------------------- IBrainDisplay

	@Override
	public void addNeuron(INeuronData _neuron) {
		addNeuron(_neuron, true);
		
		// neuron shape
		Shape3D d8Shape=new Shape3D(D8Factory.getInstance().getNewD8(4f),AppearanceTool.mkFillApp());
		
       	TransformGroup d8TG=new TransformGroup();
    	d8TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    	d8TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);         
    	d8TG.addChild(d8Shape);
    	myScene.addChild(d8TG);

		//pilotage d8
    	BehaviorMove controle=new BehaviorMove(d8TG);
		controle.setSchedulingBounds(new BoundingSphere());
		myScene.addChild(controle);
		
	}

	@Override
	public void addNeuron(INeuronData _neuron, boolean show) {
	}

	@Override
	public void deleteNeuron(INeuronData _neuron) {
		// TODO Auto-generated method stub
	}

	@Override
	public void showNeuron(INeuronData _neuron) {
		// TODO Auto-generated method stub
	}

	@Override
	public void hideNeuron(INeuronData _neuron) {
		// TODO Auto-generated method stub
	}
	
	// ----------------------------------------------- WindowListener

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(1);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
