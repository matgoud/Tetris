package vue;

import modèle.*;
import controller.*;
import java.util.Random;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Cette classe permet d'afficher la partie.
 */
public class Interface implements Ecouteur{

    private PlateauPuzzle p;
    private JFrame frame;
    private DrawPlateau dPlateau;
    private DrawPiece dPiece;

    public Interface(PlateauPuzzle p){

        this.p = p;
        //Permet à la classe d'écouter le plateau pour qu'elle se mette à jour à chque changement
        p.ajoutEcouteur(this);
        
        this.dPlateau = new DrawPlateau(p);
        this.dPiece = new DrawPiece(p);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        //Création de la fenêtre et ajout des affichages DrawPlateau et DrawPiece
        this.frame = new JFrame("Tetris");
        frame.setSize(width,height);
        frame.setLayout(new GridLayout(1,2));
        frame.add(dPlateau);
        frame.add(dPiece);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }

    /**
    * Cette méthode permet de récupérer le DrawPiece de l'interface.
    */
    public DrawPiece getDpiece(){
        return dPiece;
    }

    /**
    * Cette méthode permet de récupérer le DrawPlateau de l'interface.
    */
    public DrawPlateau getDplateau(){
        return dPlateau;
    }

    /**
    * Cette méthode permet de mettre à jour l'interface lors d'un changement dans le plateau ainsi que fermer l'interface quand la partie est terminée.
    */
    @Override
    public void MiseAJour(Object source){
        if(p.isOver()){
            frame.dispose();
        }else{
            frame.repaint();
        }
    }

}