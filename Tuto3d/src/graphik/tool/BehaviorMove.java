package graphik.tool;

/*
écrit par:Roswell
email:philgauthier_@hotmail.com

Les évenements détectées sont toujours "touche 
      préssée" représenté par la constante 
      "KeyEvent.KEY_PRESSED" et la méthode " 
      initialize()" reste inchangée. La méthode " 
      processStimulus" détermine les actions en reponse aux 
      différents stimulis. La méthode "getAWTEvent()" 
      permet de déterminé quel type de "KeyEvent.KEY_PRESSED" 
      quelle touche à été enfoncé et nous stoquons 
      les évenements dans un tableau d'évenements "AWTEvent 
      events[]". On initialise une matrice à la matrice d'identité 
      "rot" ( la matrice identité est un facteur 
      neutre pour la multiplication ). Puis on affecte une rotation de 0.1 selon 
      l'axe voulu a cette matrice. La matrice "rot" est 
      maintenant prete à &ecirc;tre multipliée avec la matrice contenant 
      l'orientation du TG. On récupere cette derniere à l'aide de 
      la méthode "getTransform(rotation)" et la 
      stoque dans la matrice "rotation", puis on les multiplie 
      et réaffecte la matrice résultant: "rotation" à 
      l'aide de la méthode "setTransform(rotation)" 
      au TG.
      Enfin il ne faut pas oublier de réactivé le mode d'attente 
      d'évenements grâce à la méthode "wakeupOn(keyEvent)". 
*/

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;

public class BehaviorMove extends Behavior
{
	private TransformGroup TG;
	private Transform3D rot=new Transform3D();
	private Transform3D rotation=new Transform3D();
	
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	public BehaviorMove(TransformGroup TG)
	{
		this.TG=TG;
	}
	
	public void initialize()
	{
		this.wakeupOn(keyEvent);
	}
		
	public void processStimulus(Enumeration criteria) {
		// on récupere le tableau d'evenements enregistres
		AWTEvent events[]=keyEvent.getAWTEvent();
		// on met la matrice a l'identite
		rot.setIdentity();
		// on recupere la matrice d'orientation actuelle du TG;
		TG.getTransform(rotation);
		
		//------ Rotation autour de l'axe Z sens positif assignée à la touche 1 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_A)
			rot.rotZ(0.1d);
		else
		//------ Rotation autour de l'axe Z sens négatif assignée à la touche 3 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Z)
			rot.rotZ(-0.1d);
		else
		//------ Rotation autour de l'axe X sens positif assignée à la touche 2 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Q)			
			rot.rotX(0.1d);
		else
		//------ Rotation autour de l'axe X sens négatif assignée à la touche 8 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_S)
			rot.rotX(-0.1d);
		else
		//------ Rotation autour de l'axe Y sens positif assignée à la touche 6 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_W)
			rot.rotY(0.1d);
		else
		//------ Rotation autour de l'axe Y sens négatif assignée à la touche 4 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_X)
			rot.rotY(-0.1d);
		
		// on applique cette rotation à la matrice courante d'orientation du TG
		rotation.mul(rot);
		// on applique la nouvelle matrice au TG que l'on manipule
		TG.setTransform(rotation);
		
		// on se remet en mode d'attente des évenements de keyEvent : touche pressée ?
		this.wakeupOn(keyEvent);
	}
}
