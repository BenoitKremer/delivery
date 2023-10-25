package Java.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// KeyListener est une interface permettant de recevoir le comportement des touches de clavier
/* KeyHandler va ajouter 3 classe de type :
 * keyPressed => Touche appuyé
 * keyReleased => Touche relaché
 * keyTyped => Touche tapé (Front Montant)
*/
public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
            // ça va renvoyer l'unicode de la touche du clavier
            int code = e.getKeyCode();
            // Condition lorsqu'un touche est enfoncé
            if (code == KeyEvent.VK_Z){
                upPressed = true ;

            }
            if (code == KeyEvent.VK_S){
                downPressed = true ;

            }
            if (code == KeyEvent.VK_Q){
                leftPressed = true ;

            }
            if (code == KeyEvent.VK_D){
                rightPressed = true ;

            }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
            // Condition lorsque les touches sont relâchés
            if (code == KeyEvent.VK_Z){
                upPressed = false ;

            }
            if (code == KeyEvent.VK_S){
                downPressed = false ;

            }
            if (code == KeyEvent.VK_Q){
                leftPressed = false ;

            }
            if (code == KeyEvent.VK_D){
                rightPressed = false ;

            }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }  
}
