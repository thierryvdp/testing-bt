package jfx8.utils;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class UserInputEvent {

	private PerspectiveCamera camera;
	private Shape3D shape;

	public UserInputEvent(Scene _scene,PerspectiveCamera _camera,Shape3D _shape) {
		camera=_camera;
		shape=_shape;
		register(_scene);
	}
	
	public void setShape(Shape3D _shape) {
		shape=_shape;
	}

	private void register(Scene scene) {

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("You pressed "+event.getCode());
				if(shape!=null) {
					double work=0;
					switch (event.getCode()) {
					case ENTER:
						System.out.println("You pressed enter");
						break;
					case W:
						work=shape.getTranslateY()-10;   
						shape.setTranslateY(work); // move up
						break;
					case S:
						work=shape.getTranslateY()+10;   
						shape.setTranslateY(work); // move down
						break;
					case A:
						work=shape.getTranslateX()-10;
						shape.setTranslateX(work); // move left
						break;
					case D:
						work=shape.getTranslateX()+10;
						shape.setTranslateX(work); // move right
						break;
					case C:
						work=shape.getTranslateZ()-10;
						shape.setTranslateZ(work); // come in
						break;
					case SPACE:
						work=shape.getTranslateZ()+10;
						shape.setTranslateZ(work); // come out
						break;

					case I:
						// rotation
						work=camera.getRotate();
						if(work==360) work=0;
						RotateTransition rotation = new RotateTransition(Duration.seconds(2), camera);
						rotation.setCycleCount(1);
						rotation.setFromAngle(work);
						rotation.setToAngle(work+10);
						rotation.setAutoReverse(false);
						rotation.setAxis(Rotate.X_AXIS);
						rotation.play();
						break;
					case K:
						break;
					case J:
						break;
					case L:
						break;
					default:
						break;
					}
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

	}
}