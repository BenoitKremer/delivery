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
        mapTileNum = new int [gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/Java/maps/map01.txt");
    }

    public void getTileImage(){

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Java/res/tiles/water.png"));


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
            while (col < gp.maxScreenCol &&  row < gp.maxScreenRow){

                String line = br.readLine();

                while(col < gp.maxScreenCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
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
        

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        //Ici on dessine le .txt plus haut
        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            int tileNum = mapTileNum[col][row];

            //Le schéma est le suivant drawImage(image, CoordX, CoordY, SizeX, SizeY, ImageObserver = null[pas besoins d'actualiser les cases avant qu'elles soit totalement construite])
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }

        }

    }
    
}
