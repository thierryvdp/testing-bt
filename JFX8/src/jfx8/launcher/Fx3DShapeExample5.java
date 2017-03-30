package jfx8.launcher;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Fx3DShapeExample5 extends Application
{
	// https://examples.javacodegeeks.com/desktop-java/javafx/javafx-3d-shapes-example/

	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) 
	{
		// Create the Sub-Scenes
		SubScene subscene1 = createSubScene(Rotate.Y_AXIS);
		SubScene subscene2 = createSubScene(Rotate.X_AXIS);
		
		// Create the HBox with both Sub-Scenes
		HBox root = new HBox(20, subscene1, subscene2);
		
		// Create a Scene with depth buffer enabled
		Scene scene = new Scene(root, 500, 300, true);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title of the Stage
		stage.setTitle("An Example with SubScenes");
		// Display the Stage
		stage.show();
	}

	private SubScene createSubScene(Point3D rotationAxis) 
	{
		// Create a Box
		Box box = new Box(100, 100, 100);
		box.setCullFace(CullFace.NONE);
		box.setTranslateX(250);
		box.setTranslateY(100);
		box.setTranslateZ(400);
		
		// Create a Camera to view the 3D Shapes
		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(100);
		camera.setTranslateY(-50);
		camera.setTranslateZ(300);	
		
		// Add a Rotation Animation to the Camera
		RotateTransition rotation = new RotateTransition(Duration.seconds(2), camera);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setFromAngle(-10);
		rotation.setToAngle(10);
		rotation.setAutoReverse(true);
		rotation.setAxis(rotationAxis);
		rotation.play();
		
		// Create a red Light
		PointLight light = new PointLight(Color.RED);
		light.setTranslateX(250);
		light.setTranslateY(-100);
		light.setTranslateZ(290);
		
		// Add the Box and the Light to the Group
		Group root = new Group(box, light);
		// Enable Rotation for the Group
		root.setRotationAxis(Rotate.X_AXIS);
		root.setRotate(30);
		
		// Create the Sub-Scene
		SubScene subscene = new SubScene(root, 200, 200, true, SceneAntialiasing.BALANCED);
		// Add the Camera to the Sub-Scene
		subscene.setCamera(camera);
		
		return subscene;
	}
}