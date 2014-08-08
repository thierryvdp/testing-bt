package chapitre2;

import java.awt.Frame;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

class Tuto2Lampe
{
	private Frame motherApp;
	private Vector3f vect;
	private static String lampeUp="texture/leopard.jpg";
	private static String lampeDown="texture/marb006.jpg";
	
	public Tuto2Lampe(Frame motherApp,Vector3f vect)
	{
		this.motherApp=motherApp;
		this.vect=vect;
	}

	public BranchGroup createLampe(String str0,String str1,String str2)
	{
		BranchGroup myScene=new BranchGroup();
		
		//les geometries
		Shape3D abatjour=new Shape3D(mkGeometry0(0.2f,0.3f,0.3f,25,new Color3f(1f,0f,0f)),createApp(str0));
		Shape3D body=new Shape3D(mkGeometry0(0.03f,0.03f,1.2f,4,new Color3f(1f,1f,0f)),createApp(str1));
		Shape3D socle=new Shape3D(mkGeometry0(0.025f,0.15f,0.1f,14,new Color3f(0f,1f,0f)),createApp(str2));
						
		//les transformations
		TransformGroup trTot=mkTgTr(vect);
		TransformGroup trD=mkTgTr(new Vector3f(0f,-0.6f,0f));
		trD.addChild(socle);
		trD.addChild(body);
		TransformGroup trU=mkTgTr(new Vector3f(0f,0.5f,0f));
		trU.addChild(abatjour);
		
		trTot.addChild(trD);
		trTot.addChild(trU);
				
		myScene.addChild(trTot);
		myScene.compile();
		return myScene;
	}
	
	private static TransformGroup mkTgTr(Vector3f vect)
	{
		Transform3D tr3d=new Transform3D();
		tr3d.setTranslation(vect);
		TransformGroup tg=new TransformGroup(tr3d);
		return tg;
	}
	
	private Geometry mkGeometry0(float radius1,float radius2,float height,int nbPane,Color3f color)
	{
		int tab[]=new int[1];
		tab[0]=(nbPane*2)+2;
		TriangleStripArray geom0=new TriangleStripArray((nbPane*2)+2,TriangleStripArray.COORDINATES|TriangleStripArray.COLOR_3|TriangleStripArray.TEXTURE_COORDINATE_2,tab);
		
		for(int i=0;i<(nbPane*2)+2;i++) geom0.setColor(i,color);  
		
		double angle=2*Math.PI/nbPane;
		
		Point3f point=new Point3f();
		for(int i=0;i<nbPane;i++)
		{
			if (i==0)
			{
				point.x=(float)(radius1*Math.cos(0*angle));
				point.y=(float)height;
				point.z=(float)(radius1*Math.sin(0*angle));
				geom0.setCoordinate(0,point);
				
				point.x=(float)(radius2*Math.cos(0*angle));
				point.y=0f;
				point.z=(float)(radius2*Math.sin(0*angle));
				geom0.setCoordinate(1,point);
				
				point.x=(float)(radius1*Math.cos(1*angle));
				point.y=(float)height;
				point.z=(float)(radius1*Math.sin(1*angle));
				geom0.setCoordinate(2,point);
				
				point.x=(float)(radius2*Math.cos(1*angle));
				point.y=0f;
				point.z=(float)(radius2*Math.sin(1*angle));
				geom0.setCoordinate(3,point);
			}
			else
			{
				point.x=(float)(radius1*Math.cos((i+1)*angle));
				point.y=(float)height;
				point.z=(float)(radius1*Math.sin((i+1)*angle));
				geom0.setCoordinate((i+1)*2,point);
				
				point.x=(float)(radius2*Math.cos((i+1)*angle));
				point.y=0f;
				point.z=(float)(radius2*Math.sin((i+1)*angle));
				geom0.setCoordinate((i+1)*2+1,point);
			} 
		}
						
		return geom0;
	}
	
	private Appearance createApp(String str)
	{
		Appearance app=new Appearance();
        	PolygonAttributes polyAttrib = new PolygonAttributes();
            	polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
            	if (str.equals("LINE")) polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
            	app.setPolygonAttributes(polyAttrib);
        	
        	return app;
	}
}