package controller;

import vue.*;
import java.awt.event.MouseAdapter;
import java.awt.event.*;
import javax.swing.*;

/**
 * Cette classe permet d'ajouter un controller à DrawPiece.
 */
public class ControllerPiece extends MouseAdapter{
    
    private DrawPiece dPiece; 
    
    //Ne sert pas à afficher DrawPiece juste à ajouter une action sur le bouton résultat
    public ControllerPiece(DrawPiece dPiece){
        this.dPiece = dPiece;

        //On ajoute une action sur le bouton résultat permettant de mettre fin à la partie
        dPiece.getResultatButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(dPiece.getPlateau().getSelectedPiece() != null){
                    JOptionPane.showMessageDialog(null,"Vous ne pouvez pas finir la partie tant qu'une pièce est sélectionnée","Attention",1);
                }else{
                    JOptionPane.showMessageDialog(null,"Partie terminée, voici votre score : "+dPiece.getPlateau().resultat(),"Score",1);
                    dPiece.getPlateau().endGame();
                }
            }
        });
    }

}