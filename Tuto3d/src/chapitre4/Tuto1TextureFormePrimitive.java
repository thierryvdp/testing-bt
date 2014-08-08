package chapitre4;

/* Chapitre 4: Section1, texturation de primitives
 * Auteur    : daboul
 * Date      : 04/00
 */

//Java standart API
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Point2f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

class Tuto1TextureFormePrimitive extends Frame implements WindowListener
{
	Tuto1TextureFormePrimitive()
	{
		super("- Chapitre 4: texturation de primitives -");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld=new SimpleUniverse(canvas);
		BranchGroup myScene=createScene(myWorld);
		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		//fin de creation
		
		this.add("Center",canvas);
	}
	
	private BranchGroup createScene(SimpleUniverse su)
	{
		BranchGroup scene=new BranchGroup();
		
		//Box texturée
		Appearance boxApp=mkAppWithTexture("stripe.gif");
		Box box=new Box(0.3f,0.4f,0.4f,Box.GENERATE_TEXTURE_COORDS,boxApp);
		TriangleStripArray tri=(TriangleStripArray)(box.getShape(Box.FRONT).getGeometry());
		tri.setTextureCoordinate(0,new Point2f(3f,0f));
		tri.setTextureCoordinate(1,new Point2f(3f,3f));
		tri.setTextureCoordinate(2,new Point2f(0f,0f));
		tri.setTextureCoordinate(3,new Point2f(0f,3f));	
		
		//Sphere texturée
		Appearance sphereApp=mkAppWithTexture("rock.gif");
		Sphere sphere=new Sphere(0.4f,Sphere.GENERATE_TEXTURE_COORDS,sphereApp);	
				
		//mise en place des objets 
		TransformGroup tg0=mkTranslation(new Vector3f(-0.4f,0f,0f));
		TransformGroup tg1=mkRotation(Math.PI/5);
		tg1.addChild(box);
		tg0.addChild(tg1);
		scene.addChild(tg0);
		
		TransformGroup tg2=mkTranslation(new Vector3f(0.52f,0f,0f));
		tg2.addChild(sphere);
		scene.addChild(tg2);
		
		scene.compile();
		return scene;
	}
	
	//creation d'une apparence qui permet la texturation
	private Appearance mkAppWithTexture(String textureName)
	{
		Appearance app=new Appearance();
	        TextureLoader loader=new TextureLoader(textureName,this);
        	ImageComponent2D image=loader.getImage();
        	
        	Texture2D texture=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
        	texture.setImage(0, image);
        	texture.setEnable(true);
        	texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        	texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        	        	
        	app.setTexture(texture);
        	app.setTextureAttributes(new TextureAttributes());
        	return app;
    	}
    	
    	//méthode de création d'un TransformGroup pour les translations
    	private TransformGroup mkTranslation(Vector3f vect)
    	{
    		Transform3D t3d=new Transform3D();
    		t3d.setTranslation(vect);
    		return new TransformGroup(t3d);
    	}
    	
    	//méthode de création d'un TransformGroup pour les rotations
    	private TransformGroup mkRotation(double angle)
    	{
    		Transform3D t3d=new Transform3D();
    		t3d.rotY(angle);
    		return new TransformGroup(t3d);
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
		Tuto1TextureFormePrimitive myApp=new Tuto1TextureFormePrimitive();
		myApp.setSize(500,300);
		myApp.setVisible(true);
	}
}

