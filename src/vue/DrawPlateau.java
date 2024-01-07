package vue;

import modèle.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

/**
 * Cette Classe permet de dessiner un plateau.
 */
public class DrawPlateau extends JPanel implements Ecouteur{

    private PlateauPuzzle p;

    public DrawPlateau(PlateauPuzzle p){
        super();
        this.p = p;
        //Permet à la classe d'écouter le plateau pour qu'elle se mette à jour à chque changement
        p.ajoutEcouteur(this);
    }
    
    /**
     * Cette méthode permet de dessiner le plateau.
     */
    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        //condition permettant de retirer l'affichage de l'interface quand la partie est terminée
        if(p.isOver()==false){
            int width = (int) getWidth();
            int height = (int) getHeight();
            
            //on récupère la matrice associée au plateau
            int[][] plateau =  p.getMatricePlateau();
            //Puis on la dessine
            for (int y = 0; y < p.getTailleY(); y++) {
                for (int x = 0; x < p.getTailleX(); x++) {
                    //Si la valeur dans la matrice est supérieur à 0 c'est qu'il y a une pièce à cet endroit 
                    //Donc on dessine la pièce
                    if(plateau[y][x]>0){
                        int col = plateau[y][x];
                        int b = col - (256 * (col/256));
                        col /= 256;
                        int gg = col - (256 * (col/256));
                        col /= 256;
                        int r = col;
                        //L'entier correspond à la couleur de la pièce
                        Color tmp = new Color(r,gg,b);
                        g.setColor(tmp);
                        g.fillRect(x*width/p.getTailleX(),y*height/p.getTailleY(),width/p.getTailleX(),height/p.getTailleY());
                    }
                }
            }
            
            for (int i = 0; i < p.getTailleX(); i++) {
                for (int j = 0; j < p.getTailleY(); j++) {
                    g.setColor(Color.BLACK);
                    g.drawRect(j*width/p.getTailleX(),i*height/p.getTailleY(),width/p.getTailleX(),height/p.getTailleY());
                }
            }
        }
    }

    /**
    * Cette méthode permet de récupérer le plateau de jeu
    */
    public PlateauPuzzle getPlateau(){
        return p;
    }

    /**
    * Cette méthode permet de mettre à jour DrawPlateau lorsqu'il y a un changement dans le plateau.
    */
    @Override
    public void MiseAJour(Object source){
        this.repaint();
    }

}