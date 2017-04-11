package jfx8.launcher;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
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
			
		float[] points = {
			-10, 10,-10, // A  0
			 10, 10, 10, // B  1
			 10, 10,-10, // C  2
			-10, 10, 10, // D  3
			-10,-10,-10, // A' 4
			 10,-10, 10, // B' 5
			 10,-10,-10, // C' 6
			-10,-10, 10  // D' 7
		};

		float[] texCoords = 
		{ 	
			0.1f, 0.5f, // 0 red     ?
	        0.3f, 0.5f, // 1 green   ?
	        0.5f, 0.5f, // 2 blue    ?
	        0.7f, 0.5f, // 3 yellow  ?
	        0.9f, 0.5f  // 4 orange  ?
	        };
		
		// ?

		int[] faces = 
		{
	
				0, 0, 1, 0, 6, 0
				,
				0, 1, 1, 1, 7, 1
				,
				0, 2, 7, 2, 6, 2
				,
				1, 3, 7, 3, 6, 3

				,
				
				2, 0, 5, 0, 4, 0
				,
				3, 1, 5, 1, 4, 1
				,
				2, 2, 3, 2, 4, 2
				,
				2, 3, 3, 3, 5, 3

		};

		// Create a TriangleMesh
		TriangleMesh mesh = new TriangleMesh();
		mesh.getPoints().addAll(points);
		mesh.getTexCoords().addAll(texCoords);
		mesh.getFaces().addAll(faces);
		
		// Create a NeshView
		MeshView meshView = new MeshView();
		meshView.setMesh(mesh);
		
	    PhongMaterial mat = new PhongMaterial();
	    mat.setDiffuseMap(new Image("file:/Users/thierry/Downloads/colors.png"));
	    meshView.setMaterial(mat);
		
		return meshView;
	}
}