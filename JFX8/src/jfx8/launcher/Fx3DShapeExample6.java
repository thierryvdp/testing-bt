package jfx8.launcher;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Fx3DShapeExample6 extends Application
{
	// https://examples.javacodegeeks.com/desktop-java/javafx/javafx-3d-shapes-example/

	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
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
		
		// Add a Rotation Animation to the Camera
		RotateTransition rt = new RotateTransition(Duration.seconds(2), camera);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setFromAngle(-30);
		rt.setToAngle(30);
		rt.setAutoReverse(true);
		rt.setAxis(Rotate.Y_AXIS);
		rt.play();
		
		// Create the red Front Light
		PointLight redLight = new PointLight();
		redLight.setColor(Color.RED);
		redLight.setTranslateX(250);
		redLight.setTranslateY(150);
		redLight.setTranslateZ(300);

		// Create the green Back Light
		PointLight greenLight = new PointLight();
		greenLight.setColor(Color.GREEN);
		greenLight.setTranslateX(200);
		greenLight.setTranslateY(150);
		greenLight.setTranslateZ(450);
		
		// Add the Shapes and the Light to the Group		
		Group root = new Group(meshView, redLight, greenLight);
		// Rotate the triangle with its lights to 90 degrees
		root.setRotationAxis(Rotate.Y_AXIS);
		root.setRotate(90);
		
		// Create a Scene with depth buffer enabled
		Scene scene = new Scene(root, 400, 300, true);
		// Add the Camera to the Scene
		scene.setCamera(camera);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title of the Stage
		stage.setTitle("An Example using a TriangleMesh");
		// Display the Stage
		stage.show();
	}
	
	public MeshView createMeshView() 
	{
		float[] points = 
		{	
			50, 0, 0,
			45, 10, 0,
			55, 10, 0
		};
		
		float[] texCoords = 
		{ 	
			0.5f, 0.5f,
			0.0f, 1.0f,
			1.0f, 1.0f
		};
		
		int[] faces = 
		{
			0, 0, 2, 2, 1, 1,
			0, 0, 1, 1, 2, 2
		};
		
		// Create a TriangleMesh
		TriangleMesh mesh = new TriangleMesh();
		mesh.getPoints().addAll(points);
		mesh.getTexCoords().addAll(texCoords);
		mesh.getFaces().addAll(faces);
		
		// Create a NeshView
		MeshView meshView = new MeshView();
		meshView.setMesh(mesh);
		
		return meshView;
	}
}