package Java.tiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import Java.main.GamePanel;
import java.awt.Graphics2D;

public class TileManager {


    // On place les "tiles" (cases) dans une liste pour pouvoir les manipuler plus facilement
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp =gp;

        tile = new Tile[10];    //Ici on définis une liste de 10 type de tiles ex(grass, water, tree, mountain,...)
        mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/Java/maps/world01.txt");
    }

    public void getTileImage(){

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/water.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/tree.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/sand.png"));

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            //Ici on créer une boucle permettant de lire le background avec un fichier .txt comme modèle
            //Cela permet d'éviter d'écrire les cases et les maps une par une et d'avoir une gestion plus rapide et visible 
            //Dans le cas où il y a plusieurs maps
            while (col < gp.maxWorldCol &&  row < gp.maxWorldRow){

                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }  
            }
            br.close();
        }
        catch(Exception e){

        }


    }
    //Permet de dessiner les tiles
    public void draw(Graphics2D g2){
        

        int worldCol = 0;
        int worldRow = 0;

        //Ici on dessine le .txt plus haut
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];


            // C'est ici qu'on va simuler une caméra avec le player au centre 
            // ça va déplacer l'origine qui est en haut a gauche de l'écran
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // Ici on permet en plus de bloquer la limite dans le cas où le player arrive en bout de map
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // cette boucle permet d'afficher que les cases dans le perimètre du player
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                //Le schéma est le suivant drawImage(image, CoordX, CoordY, SizeX, SizeY, ImageObserver = null[pas besoins d'actualiser les cases avant qu'elles soit totalement construite])
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }

  
            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }    
}
