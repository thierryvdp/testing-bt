package chapitre3;

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

public class Tuto6Behavior2 extends Behavior
{
	private TransformGroup TG;
	private Transform3D rot=new Transform3D();
	private Transform3D rotation=new Transform3D();
	
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	public Tuto6Behavior2(TransformGroup TG)
	{
		this.TG=TG;
	}
	
	public void initialize()
	{
		this.wakeupOn(keyEvent);
	}
		
	public void processStimulus(Enumeration criteria)
	{
		// on récupere le tableau d'evenements enregistres
		AWTEvent events[]=keyEvent.getAWTEvent();
		// on met la matrice a l'identité
		rot.setIdentity();
		// on recupere la matrice d'orientation actuelle du TG;
		TG.getTransform(rotation);
		
		//------ Rotation autour de l'axe Z sens positif assignée a la touche 1 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_A)
			rot.rotZ(0.1d);
		else
		//------ Rotation autour de l'axe Z sens négatif assignée a la touche 3 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Z)
			rot.rotZ(-0.1d);
		else
		//------ Rotation autour de l'axe X sens positif assignée a la touche 2 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Q)			
			rot.rotX(0.1d);
		else
		//------ Rotation autour de l'axe X sens négatif assignée a la touche 8 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_S)
			rot.rotX(-0.1d);
		else
		//------ Rotation autour de l'axe Y sens positif assignée a la touche 6 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_W)
			rot.rotY(0.1d);
		else
		//------ Rotation autour de l'axe Y sens négatif assignée a la touche 4 du pavé numérique--------
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_X)
			rot.rotY(-0.1d);
		
		// on applique cette rotation a la matrice courante d'orientation du TG
		rotation.mul(rot);
		// on applique la nouvelle matrice au TG que l'on manipule
		TG.setTransform(rotation);
		
		// on se remet en mode d'attente des évenements de keyEvent : touche pressée ?
		this.wakeupOn(keyEvent);
	}
}
