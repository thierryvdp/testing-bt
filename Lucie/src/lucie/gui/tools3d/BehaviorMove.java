package lucie.gui.tools3d;

/*
�crit par:Roswell
email:philgauthier_@hotmail.com

Les �venements d�tect�es sont toujours "touche 
      pr�ss�e" repr�sent� par la constante 
      "KeyEvent.KEY_PRESSED" et la m�thode " 
      initialize()" reste inchang�e. La m�thode " 
      processStimulus" d�termine les actions en reponse aux 
      diff�rents stimulis. La m�thode "getAWTEvent()" 
      permet de d�termin� quel type de "KeyEvent.KEY_PRESSED" 
      quelle touche � �t� enfonc� et nous stoquons 
      les �venements dans un tableau d'�venements "AWTEvent 
      events[]". On initialise une matrice � la matrice d'identit� 
      "rot" ( la matrice identit� est un facteur 
      neutre pour la multiplication ). Puis on affecte une rotation de 0.1 selon 
      l'axe voulu a cette matrice. La matrice "rot" est 
      maintenant prete � &ecirc;tre multipli�e avec la matrice contenant 
      l'orientation du TG. On r�cupere cette derniere � l'aide de 
      la m�thode "getTransform(rotation)" et la 
      stoque dans la matrice "rotation", puis on les multiplie 
      et r�affecte la matrice r�sultant: "rotation" � 
      l'aide de la m�thode "setTransform(rotation)" 
      au TG.
      Enfin il ne faut pas oublier de r�activ� le mode d'attente 
      d'�venements gr�ce � la m�thode "wakeupOn(keyEvent)". 
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
		// on r�cupere le tableau d'evenements enregistres
		AWTEvent events[]=keyEvent.getAWTEvent();
		// on met la matrice a l'identite
		rot.setIdentity();
		// on recupere la matrice d'orientation actuelle du TG;
		TG.getTransform(rotation);
		
		//------ Rotation autour de l'axe Z sens positif assign�e � la touche 1 du pav� num�rique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_A)
			rot.rotZ(0.1d);
		else
		//------ Rotation autour de l'axe Z sens n�gatif assign�e � la touche 3 du pav� num�rique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Z)
			rot.rotZ(-0.1d);
		else
		//------ Rotation autour de l'axe X sens positif assign�e � la touche 2 du pav� num�rique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Q)			
			rot.rotX(0.1d);
		else
		//------ Rotation autour de l'axe X sens n�gatif assign�e � la touche 8 du pav� num�rique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_S)
			rot.rotX(-0.1d);
		else
		//------ Rotation autour de l'axe Y sens positif assign�e � la touche 6 du pav� num�rique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_W)
			rot.rotY(0.1d);
		else
		//------ Rotation autour de l'axe Y sens n�gatif assign�e � la touche 4 du pav� num�rique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_X)
			rot.rotY(-0.1d);
		
		// on applique cette rotation � la matrice courante d'orientation du TG
		rotation.mul(rot);
		// on applique la nouvelle matrice au TG que l'on manipule
		TG.setTransform(rotation);
		
		// on se remet en mode d'attente des �venements de keyEvent : touche press�e ?
		this.wakeupOn(keyEvent);
	}
}
