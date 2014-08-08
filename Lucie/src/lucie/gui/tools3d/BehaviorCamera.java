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

public class BehaviorCamera extends Behavior
{
	private TransformGroup TG;
	private Transform3D rot=new Transform3D();
	private Transform3D rotation=new Transform3D();
	
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	public BehaviorCamera(TransformGroup TG)
	{
		this.TG=TG;
	}
	
	public void initialize()
	{
		this.wakeupOn(keyEvent);
	}
		
	public void processStimulus(Enumeration criteria)
	{
		// on r�cupere le tableau d'evenements enregistres
		AWTEvent events[]=keyEvent.getAWTEvent();
		// on met la matrice � l'identit�
		rot.setIdentity();
		// on recupere la matrice d'orientation actuelle du TG;
		TG.getTransform(rotation);
		
		//------ Rotation autour de l'axe Z sens positif
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_T)
			rot.rotZ(0.1d);
		else
		//------ Rotation autour de l'axe Z sens n�gatif
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_Y)
			rot.rotZ(-0.1d);
		else
		//------ Rotation autour de l'axe X sens positif
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_G)			
			rot.rotX(0.1d);
		else
		//------ Rotation autour de l'axe X sens n�gatif
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_H)
			rot.rotX(-0.1d);
		else
		//------ Rotation autour de l'axe Y sens positif
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_B)
			
			rot.rotY(0.1d);
		else
		//------ Rotation autour de l'axe Y sens n�gatif
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_N)
			rot.rotY(-0.1d);
		
		// on applique cette rotation � la matrice courante d'orientation du TG
		rotation.mul(rot);
		
		// on applique la nouvelle matrice au TG que l'on manipule
		TG.setTransform(rotation);
		
		// on se remet en mode d'attente des �venements de keyEvent : touche press�e ?
		this.wakeupOn(keyEvent);
	}
}
