package jfx8.launcher;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.stage.Stage;

public class Fx3DShapeExample3 extends Application
{
	
	// https://examples.javacodegeeks.com/desktop-java/javafx/javafx-3d-shapes-example/
	// Specifying the Draw Mode of Shape
	// Move Shape
	// Handle keyboard Mouse
	
	private double cubX=150;
	private double cubY=0;
	private double cubZ=400;
	private Box box;

	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) 
	{
		// Create a Box
		box = new Box(100, 100, 100);
		box.setDrawMode(DrawMode.LINE);
		box.setTranslateX(150);
		box.setTranslateY(0);
		box.setTranslateZ(400);
		
		// Create a Light
		PointLight light = new PointLight();
		light.setTranslateX(350);
		light.setTranslateY(100);
		light.setTranslateZ(300);

		// Create a Camera to view the 3D Shapes
		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(100);
		camera.setTranslateY(-50);
		camera.setTranslateZ(300);

		
		// Add the Shapes and the Light to the Group
		Group root = new Group(box, light);
		
		// Create a Scene with depth buffer enabled
		Scene scene = new Scene(root, 400, 200, true);
		// Add the Camera to the Scene
		scene.setCamera(camera);
		
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
    			switch (event.getCode()) {
    			case ENTER:
    		          System.out.println("You pressed enter");
    				break;
    			case UP:
    				cubY=cubY-10;
    				box.setTranslateY(cubY);
    				break;
    			case DOWN:
    				cubY=cubY+10;
    				box.setTranslateY(cubY);
    				break;
    			case LEFT:
    				cubX=cubX-10;
    				box.setTranslateX(cubX);
    				break;
    			case RIGHT:
    				cubX=cubX+10;
    				box.setTranslateX(cubX);
    				break;

    			default:
    				break;
    			}
            }
        });
		
		EventHandler<MouseEvent> mouseEntered = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse Enter");
			}
		};
		EventHandler<MouseEvent> mouseExited = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse Exit");
			}
		};
		EventHandler<MouseEvent> mouseClicked = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse Click");
			}
		};
		scene.setOnMouseEntered(mouseEntered);
		scene.setOnMouseExited(mouseExited);
		scene.setOnMouseClicked(mouseClicked);
		
		EventHandler<ScrollEvent> scrollEvent = new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent e) {
				System.out.println("scrolling"+e.toString());
			}
		};
		
		scene.setOnScroll(scrollEvent);
		
        
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title of the Stage
		stage.setTitle("An Example with specified Draw Mode");
		// Display the Stage
		stage.show();		
	}
}