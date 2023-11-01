package Java.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Java.main.GamePanel;
import Java.main.KeyHandler;


// liaison avec Entity, GamePanel et KeyHandler
public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    //Données du Player
    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    //Images du Player
    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightUpAFK.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightUpMove.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightDownAFK.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightDownMove.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightLeftAFK.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightLeftMove.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightRightAFK.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Java/res/Player/KnightRightMove.png"));


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //Permet les mouvements du Player
    public void update() {

        //cela permet d'animer le sprite seulement lorsque qu'il se déplace
        if(keyH.upPressed == true
         || keyH.downPressed == true 
         || keyH.leftPressed == true 
         || keyH.rightPressed == true){

            /*Cette partie permet "d'animer" le sprite 
             * c'est ce qui va faire le changement entre
            la frame(image) 1 et la frame 2 */
            spriteCounter++;
            if(spriteCounter > 10){ //Le 10 permet de changer toutes les 10frames DU JEU donc à 60 FPS 6 changements par secondes
                if(spriteNum ==1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            
        }
        
        if(keyH.upPressed == true) {
            direction = "up";
            y -= speed;
        }
        else if (keyH.downPressed == true){
            direction = "down";
            y += speed;
        }
        else if (keyH.leftPressed == true){
            direction = "left";
            x -= speed;
        }
        else if (keyH.rightPressed == true){
            direction = "right";
            x += speed;
        }

        
    }

    //Partie graphique (ou draw)
    public void draw(Graphics2D g2) {

        //Contrôle de l'affichage en fonction de la direction
        BufferedImage image = null;

        switch(direction){
        case "up":
            if(spriteNum == 1){
                image = up1; //Frame1
            }
            if(spriteNum == 2){
                image = up2; //Frame2
            }
            break;
        case "down":
            if(spriteNum == 1){
                image = down1;
            }
            if(spriteNum == 2){
                image = down2;
            }
            break;
        case "left":
            if(spriteNum == 1){
                image = left1;
            }
            if(spriteNum == 2){
                image = left2;
            }
            break;
        case "right":
            if(spriteNum == 1){
                image = right1;
            }
            if(spriteNum == 2){
                image = right2;
            }
            break;
        
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
