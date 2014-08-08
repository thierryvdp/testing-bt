package chapitre3;

/*
écrit par:Roswell
email:philgauthier_@hotmail.com

Nous allons maintenant vous permettre d'interagir avec votre environnement 
  grâce à la classe " Behavior" . C'est grace à elle 
  que vous allez pouvoir faire correspondre des évenements à des 
  action^s dans votre monde 3d. Il est pratique de définir vos interactions 
  dans une classe dérivant de " Behavior" . Cette nouvelle classe 
  doit au moins avoir un constructeur contenant le TG à modifier, une méthode 
  d'initialistion : " initialisatize"  définissant les évenements 
  à détecter, et enfin une méthode " processStimulus"  
  decrivant les actions déclanchées par les stimulus.
Nous allons commencer par constuire une classe qui lors de la touche du clavier 
  fera tourner un objet " box" . L'évènement à détecter est donc 
  " KeyEvent.KEY_PRESSED" . On utilise la méthode " wakeupOn()"  
  héritée de la classe " Behavior"  avec un évènement pour 
  spécifier quel évènement doit être détecté 
  par notre classe.  
*/

// classes Java 3d
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;

// rotion autour de l'axe Y sur pression d'une touche
public class Tuto5Behavior1 extends Behavior
{

	// variable de travail
	private TransformGroup targetTG;
	private Transform3D rotation = new Transform3D();
	private double angle = 0.0;

	// constructeur
	public Tuto5Behavior1(TransformGroup targetTG)
	{
		this.targetTG = targetTG;
	}

	// on définit les évenements à détecter
	public void initialize()
	{
		this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
	}

	// on définit les actions en réponse
	public void processStimulus(Enumeration criteria)
	{
		// actions :rotation autour de l'axe Y
		angle += 0.1;
		rotation.rotY(angle);
		targetTG.setTransform(rotation);
		this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
	}

} 