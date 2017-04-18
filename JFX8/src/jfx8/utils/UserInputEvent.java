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
	private boolean mouseclick;

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
				RotateTransition rotation = null;
				double work=0;
				double rot=0;
				if(shape!=null) {
					if(mouseclick) {
						work=camera.getRotate();
						rotation = new RotateTransition(Duration.seconds(2), camera);
						rotation.setCycleCount(1);
						rotation.setAutoReverse(false);
						rotation.setAxis(Rotate.X_AXIS);
					}
					switch (event.getCode()) {
					case W:
						if(mouseclick) {
							rot=work+10;
							rotation.setAxis(Rotate.X_AXIS);
						}
						else {
							work=shape.getTranslateY()-10;   
							shape.setTranslateY(work); // move up
						}
						break;
					case S:
						if(mouseclick) {
							rot=work-10;
							rotation.setAxis(Rotate.X_AXIS);
						}
						else {
							work=shape.getTranslateY()+10;   
							shape.setTranslateY(work); // move down
						}
						break;
					case A:
						if(mouseclick) {
							rot=work+10;
							rotation.setAxis(Rotate.Y_AXIS);
						}
						else {
							work=shape.getTranslateX()-10;
							shape.setTranslateX(work); // move left
						}
						break;
					case D:
						if(mouseclick) {
							rot=work-10;
							rotation.setAxis(Rotate.Y_AXIS);
						}
						else {
							work=shape.getTranslateX()+10;
							shape.setTranslateX(work); // move right
						}
						break;
					case C:
						if(mouseclick) {
							rot=work+10;
							rotation.setAxis(Rotate.Z_AXIS);
						}
						else {
							work=shape.getTranslateZ()-10;
							shape.setTranslateZ(work); // come in
						}
						break;
					case SPACE:
						if(mouseclick) {
							rot=work-10;
							rotation.setAxis(Rotate.Z_AXIS);
						}
						else {
							work=shape.getTranslateZ()+10;
							shape.setTranslateZ(work); // come out
						}
						break;
					}
					
					if(mouseclick) {
//						work=work ^ 360;
//						rot=rot ^ 360;
						rotation.setFromAngle(work);
						rotation.setToAngle(rot);
						rotation.play();
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
				mouseclick=!mouseclick;
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