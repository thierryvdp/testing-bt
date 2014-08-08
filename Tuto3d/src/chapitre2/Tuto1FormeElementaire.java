package chapitre2;
/* Chapitre 2: Section 1, création de geometries elementaires
 * Auteur    : daboul 
 * Date      : 03/00
 */

//Java standart API
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.LineArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleFanArray;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

class Tuto1FormeElementaire extends Frame implements WindowListener
{
	Tuto1FormeElementaire()
	{
		super("- Chapitre 2 : geometries élementaires -");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld=new SimpleUniverse(canvas);
		BranchGroup myScene=createScene();
		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		//fin de creation
		
		this.add("Center",canvas);
	}
	
	BranchGroup createScene()
	{
		BranchGroup scene=new BranchGroup();
						
		//ligne des X
		LineArray axisX=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
		axisX.setCoordinate(0,new Point3f(-1f,0f,0f));
		axisX.setCoordinate(1,new Point3f(1f,0f,0f));
		axisX.setColor(0,new Color3f(1f,0f,0f));
		axisX.setColor(1,new Color3f(0f,0f,1f));
		
		//ligne des Y
		LineArray axisY=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
		axisY.setCoordinate(0,new Point3f(0f,-1f,0f));
		axisY.setCoordinate(1,new Point3f(0f,1f,0f));
		axisY.setColor(0,new Color3f(0f,1f,0f));
		axisY.setColor(1,new Color3f(1f,0f,0f));
		
		//triangle
		TriangleArray triangle=new TriangleArray(3,TriangleArray.COORDINATES|TriangleArray.COLOR_3);
		triangle.setCoordinate(0,new Point3f(-0.8f,0.2f,0f));
		triangle.setCoordinate(1,new Point3f(-0.2f,0.2f,0f));
		triangle.setCoordinate(2,new Point3f(-0.5f,0.8f,0f));
		triangle.setColor(0,new Color3f(1f,0f,0f));
		triangle.setColor(1,new Color3f(0f,1f,0f));
		triangle.setColor(2,new Color3f(0f,0f,1f));
		
		//quadrilatère
		QuadArray quad=new QuadArray(8,QuadArray.COORDINATES|QuadArray.COLOR_3);
		quad.setCoordinate(0,new Point3f(0.2f,0.4f,0f));
		quad.setCoordinate(1,new Point3f(0.6f,0.4f,0f));
		quad.setCoordinate(2,new Point3f(0.6f,0.8f,0f));
		quad.setCoordinate(3,new Point3f(0.2f,0.8f,0f));
		quad.setCoordinate(4,new Point3f(0.6f,0.2f,0f));
		quad.setCoordinate(5,new Point3f(0.8f,0.2f,0f));
		quad.setCoordinate(6,new Point3f(0.8f,0.4f,0f));
		quad.setCoordinate(7,new Point3f(0.6f,0.4f,0f));
		for(int i=0;i<4;i++) quad.setColor(i,new Color3f(1f,1f,0f));
		for(int i=4;i<8;i++) quad.setColor(i,new Color3f(0f,0f,1f));
		
		//triangles enchainés
		int tab[]=new int[1];
		tab[0]=4;
		TriangleStripArray trStrip=new TriangleStripArray
			(4
			,TriangleStripArray.COORDINATES|TriangleStripArray.COLOR_3
			,tab);
		trStrip.setCoordinate(0,new Point3f(-0.8f,-0.8f,0f));
		trStrip.setCoordinate(1,new Point3f(-0.5f,-0.8f,0f));
		trStrip.setCoordinate(2,new Point3f(-0.5f,-0.2f,0f));  
		trStrip.setCoordinate(3,new Point3f(-0.2f,-0.2f,0f));  
		for(int i=0;i<4;i++) trStrip.setColor(i,new Color3f(1f,0f,1f));
		
		//triangles avec sommet commun
		tab[0]=5;
		TriangleFanArray trFan=new TriangleFanArray
			(5
			,TriangleFanArray.COORDINATES|TriangleFanArray.COLOR_3
			,tab);
		trFan.setCoordinate(0,new Point3f(0.2f,-0.2f,0f));
		trFan.setCoordinate(1,new Point3f(0.2f,-0.8f,0f));
		trFan.setCoordinate(2,new Point3f(0.4f,-0.75f,0f));
		trFan.setCoordinate(3,new Point3f(0.6f,-0.6f,0f));
		trFan.setCoordinate(4,new Point3f(0.75f,-0.3f,0f));
		trFan.setColor(0,new Color3f(1f,0f,0f));
		for(int i=1;i<5;i++) trFan.setColor(i,new Color3f(0f,1f,0f));
					
		scene.addChild(new Shape3D(axisX));
		scene.addChild(new Shape3D(axisY));
		scene.addChild(new Shape3D(triangle));
		scene.addChild(new Shape3D(quad));
		scene.addChild(new Shape3D(trStrip,mkFillApp()));
		scene.addChild(new Shape3D(trFan,mkFillApp()));
				
		scene.compile();
		return scene;
	}
	
	Appearance mkFillApp()
	{
		Appearance app=new Appearance();
		app.setPolygonAttributes(new PolygonAttributes
			(PolygonAttributes.POLYGON_LINE
			,PolygonAttributes.CULL_NONE
			,0f));
		return app;
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
		Tuto1FormeElementaire myApp=new Tuto1FormeElementaire();
		myApp.setSize(400,400);
		myApp.setVisible(true);
	}
}

