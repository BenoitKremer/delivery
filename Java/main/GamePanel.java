package Java.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Java.entity.Player;

public class GamePanel extends JPanel implements Runnable{

    //PARAMÈTRES D'ÉCRAN
    //Ici la taille des toutes les entités sera donc de 16x16
    final int originalTileSize = 16;
    //Échelle qui augmente la taille des entités par rapport aux écrans modernes
    final int scale = 3;
    //Définis la taille d'une tuile (case)
    public final int tileSize = originalTileSize * scale; //16*3 = 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;    //48*16 = 768px
    final int screenHeight = tileSize * maxScreenRow;  //48*12 = 576px

    // FPS, IPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();

    //Le Thread est ce qui va permettre le rafraichissement de l'écran ce qu'on appelle les FPS ou IPS en français
    Thread gameThread;
    Player player = new Player(this, keyH);

    //Postition par défaut du joueur
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    //On dimensionne l'écran d'affichage avec les précedentes valeurs
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); // ça permet en gros d'animer en temps réel (par exemple si j'appuie sur une toucher pour aller à droite cette zone va garder le "focus" pour pouvoir l'afficher à la prochaine frame)
    
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    //Cette class là se lance en même temps que le Thread c'est ce qui va permettre de faire la boucle du jeu
    @Override
    public void run() {
        /*Vu qu'on utilise les nanosecondes il faut
         * diviser par 1 000 000 000 les images par SECONDES
         * cela va permettre de définir la fréquence de rafraîchissement
        */
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        //Cela va permettre l'exécution permanante du jeu 
        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta>= 1){

                //nanoTime() Donne le temps d'exécution qui va permettre de dire au pc d'éxécuter la commande à 60 fps
                
                //Les objectifs principaux de la boucle du jeu est de 
                //Update n Draw
                update();

                // Il s'agit de la class paintComponent()
                // C'est ue appelation spécial
                repaint();

                delta--;

                drawCount++;
                
                /*
                 * Il existe aussi une autre méthode 
                 * permettant de faire une game loop
                 * nommé sleep ci-dessous:
                    try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    // la fonction sleep accepte que les millisecondes donc il faut faire une conversion
                    remainingTime =remainingTime/1000000;
                    // Dans le cas où le refresh dépasse 1/60s
                    if(remainingTime < 0) {
                        remainingTime = 0;
                    }
                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;

                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 */
            }
            //Boucle qui permet d'afficher les FPS
            if (timer >= 1000000000){
                System.out.println("FPS :" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }
    //Class d'actualisation
    public void update(){

        player.update();
    }

    // PaintComponent permet de dessiner sur le JFrame Panel
    //Graphics est une class qui a plein de fonction pour dessiner
    public void paintComponent(Graphics g){
        //Va de pair avec la classe
        super.paintComponent(g);

        //Permet un meilleur contrôle géographique/cartésien/couleurs/textes
        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        // Pour garder les informations en mémoire
        g2.dispose();
    }
}
