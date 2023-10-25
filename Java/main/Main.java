package Java.main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){

        //Création d'une fenêtre
        JFrame window =new JFrame();
        //Pour pouvoir fermer la fenêtre
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Pour ne pas avoir a Resize la fenêtre
        window.setResizable(false);
        //Pour un titre à la fenêtre
        window.setTitle("2D Adventure");

        //Ajout du GamePanel à la fenêtre
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        //Pour positionner la fenêtre au centre
        window.setLocationRelativeTo(null);
        //Pour afficher la fenêtre
        window.setVisible(true);

        gamePanel.startGameThread();
    }    
}
