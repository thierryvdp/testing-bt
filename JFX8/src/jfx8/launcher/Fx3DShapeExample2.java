package jfx8.launcher;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class Fx3DShapeExample2 extends Application {
	
	// https://examples.javacodegeeks.com/desktop-java/javafx/javafx-3d-shapes-example/
	// Specifying the Shape Material
		
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) 
	{
		// Create a Box
		Box box = new Box(100, 100, 100);
		box.setTranslateX(250);
		box.setTranslateY(0);
		box.setTranslateZ(400);

		// Create the Material
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.TAN);
		// Set the material for the box
		box.setMaterial(material);
		
		// Create a Box with texture
		Box textbox = new Box(100, 100, 100);
		textbox.setTranslateX(450);
		textbox.setTranslateY(50);
		textbox.setTranslateZ(400);

		// Create the Material
		PhongMaterial textureMaterial = new PhongMaterial();
		// Create the Image
		Image image = new Image("file:/Users/thierry/Downloads/merkabah.jpg"); // chemin absolu sur le disque
		textureMaterial.setDiffuseColor(Color.BEIGE);
		textureMaterial.setDiffuseMap(image);
		// Set the material for the box
		textbox.setMaterial(textureMaterial);
		
		// Create a Light 		
		PointLight light = new PointLight();
		light.setTranslateX(250);
		light.setTranslateY(100);
		light.setTranslateZ(300);
						
		// Create a Camera to view the 3D Shapes
		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(200);
		camera.setTranslateY(-50);
		camera.setTranslateZ(300);

		// Create the Group with both Boxes
		Group root = new Group(box, textbox);
		
		// Create a Scene with depth buffer enabled
		Scene scene = new Scene(root, 400, 200, true);
		// Add the Camera to the Scene
		scene.setCamera(camera);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title of the Stage
		stage.setTitle("An Example with specified Material");
		// Display the Stage
		stage.show();		
	}
}