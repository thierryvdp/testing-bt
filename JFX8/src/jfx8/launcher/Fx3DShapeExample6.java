package jfx8.launcher;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;
import jfx8.utils.UserInputEvent;

public class Fx3DShapeExample6 extends Application
{
	// https://examples.javacodegeeks.com/desktop-java/javafx/javafx-3d-shapes-example/
	// Creating User-Defined Shapes

	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	private UserInputEvent eventHandler;
	
	@Override
	public void start(Stage stage) 
	{
		// Create a MeshView
		MeshView meshView = this.createMeshView();
		meshView.setTranslateX(250);
		meshView.setTranslateY(100);
		meshView.setTranslateZ(400);
		
		// Scale the Meshview to make it look bigger
		meshView.setScaleX(10.0);
		meshView.setScaleY(10.0);
		meshView.setScaleZ(10.0);
		
		// Create a Camera to view the 3D Shapes
		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(100);
		camera.setTranslateY(-50);
		camera.setTranslateZ(300);
		
		// Create Light
		PointLight light = new PointLight();
		light.setTranslateX(250);
		light.setTranslateY(150);
		light.setTranslateZ(300);
		light.setColor(Color.GREEN);


		
		// Add the Shapes and the Light to the Group		
		Group root = new Group(meshView, light);
		
		// Create a Scene with depth buffer enabled
		Scene scene = new Scene(root, 400, 300, true);
		// Add the Camera to the Scene
		scene.setCamera(camera);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title of the Stage
		stage.setTitle("An Example using a TriangleMesh");
		
		eventHandler = new UserInputEvent(scene, camera, meshView);
		
		// Display the Stage
		stage.show();
	}
	
	public MeshView createMeshView() 
	{

		// http://stackoverflow.com/questions/26831871/coloring-individual-triangles-in-a-triangle-mesh-on-javafx
		
//		3D point (x, y, z)
//		X-axis pointing to the right
//		Y-axis pointing down
//		Z-axis pointing away from the viewer or into the screen
				
		float[] points = {
			-10,-10, 10, // A  0   B  A  D'  107
			 10,-10,-10, // B  1   A  B  C'  016
			 10,-10, 10, // C  2   D' C' B   761
			-10,-10,-10, // D  3   C' D' A   670
			-10, 10, 10, // A' 4   C  D  B'  235
			 10, 10,-10, // B' 5   D  C  A'  324
			 10, 10, 10, // C' 6   B' A' C   542
			-10, 10,-10  // D' 7   A' B' D   453
		};
		
//		Start with the texture coordinate collection, 
//		because you can pretty much ignore it for this simple pyramid. 
//		Texture coordinates are useful when you’re using a material that contains an image 
//		that should be stretched in a specific way over the framework of the mesh. 
//		They allow you to associate a specific x-, y-coordinate in the image with each corner of each face.
//
//		Unfortunately, you can’t simply leave out the texture coordinates 
//		even if you don’t need them, so you must load at least one coordinate. 
//		Do that with this line of code:

		float[] texCoords = 
		{ 0f, 0f };
		
		// ?

		// rendered face : counter-clockwise : sens inverse des aiguilles
		int[] faces = 
		{
				1, 0, 0, 0, 7, 0
				,
				0, 0, 1, 0, 6, 0
				,
				7, 0, 6, 0, 1, 0
				,
				6, 0, 7, 0, 0, 0
				,
				2, 0, 3, 0, 5, 0
				,
				3, 0, 2, 0, 4, 0
				,
				5, 0, 4, 0, 2, 0
				,
				4, 0, 5, 0, 3, 0
		};

		// Create a TriangleMesh
		TriangleMesh mesh = new TriangleMesh();
		mesh.getPoints().addAll(points);
		mesh.getTexCoords().addAll(texCoords);
		mesh.getFaces().addAll(faces);

		// CullFace.NONE option Then the back faces are black

		// Create a MeshView
		MeshView meshView = new MeshView();
		meshView.setMesh(mesh);
//		meshView.setCullFace(CullFace.NONE);
		meshView.setDrawMode(DrawMode.LINE);
		
		return meshView;
		
	}
}