package controller;

import vue.*;
import modèle.*;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import javax.swing.*;

/**
 * Cette Classe permet d'initialiser le controller DrawPlateau.
 */
public class ControllerPlateau extends MouseAdapter{

    private DrawPlateau dPlateau;
    private DrawPiece dPiece;
    private Position initialPos;
    private Position lastPos; 

    public ControllerPlateau(DrawPlateau dPlateau,DrawPiece dPiece){

        this.dPlateau = dPlateau;
        this.dPiece = dPiece;
        this.initialPos = null;
        this.lastPos = null;

        //On ajoute les MouseListener sur JPanel de DrawPlateau 
        dPlateau.addMouseListener(this);
        dPlateau.addMouseMotionListener(this);

    }
        
    /**
     * Cette méthode permet de déplacer la pièce sélectionner tant que le clic est enfoncé.
     */
    public void mouseDragged(MouseEvent e) {

        int x = e.getX()/(dPlateau.getWidth()/dPlateau.getPlateau().getTailleX());
        int y = e.getY()/(dPlateau.getHeight()/dPlateau.getPlateau().getTailleY());
        Position newPos = new Position(x,y);

        //Si aucune pièce est sélectionnée on ne fait rien
        if(dPlateau.getPlateau().getSelectedPiece() == null){
            return;
        }else{
            //Ces 4 condition empêche le placement d'une pièce en dehors du plateau
            if(x<dPlateau.getPlateau().getSelectedPiece().getRotatedTailleX()/2){
                dPlateau.getPlateau().getSelectedPiece().setPos(lastPos);
                dPlateau.getPlateau().movePiece(dPlateau.getPlateau().getSelectedPiece());
            }else if(y<dPlateau.getPlateau().getSelectedPiece().getRotatedTailleY()/2){
                dPlateau.getPlateau().getSelectedPiece().setPos(lastPos);
                dPlateau.getPlateau().movePiece(dPlateau.getPlateau().getSelectedPiece());
            }else if(x>(dPlateau.getPlateau().getTailleX())-dPlateau.getPlateau().getSelectedPiece().getRotatedTailleX()/2-(dPlateau.getPlateau().getSelectedPiece().getRotatedTailleX()%2)){
                dPlateau.getPlateau().getSelectedPiece().setPos(lastPos);
                dPlateau.getPlateau().movePiece(dPlateau.getPlateau().getSelectedPiece());
            }else if(y>(dPlateau.getPlateau().getTailleY())-dPlateau.getPlateau().getSelectedPiece().getRotatedTailleY()/2-(dPlateau.getPlateau().getSelectedPiece().getRotatedTailleY()%2)){
                dPlateau.getPlateau().getSelectedPiece().setPos(lastPos);
                dPlateau.getPlateau().movePiece(dPlateau.getPlateau().getSelectedPiece());
            }else{
                //On change la position de la pièce
                dPlateau.getPlateau().getSelectedPiece().setPos(newPos);
                //Si la pièce peut être bougée sans collision avec une notre alors on la bouge et on stocke ça position
                if(dPlateau.getPlateau().movePiece(dPlateau.getPlateau().getSelectedPiece())){
                    lastPos = newPos;
                }else{
                    //Si la pièce ne peut pas être bouger car il y a collision avec une notre pièce alors on la bouge à sa dernière position valide
                    dPlateau.getPlateau().getSelectedPiece().setPos(lastPos);                
                    dPlateau.getPlateau().movePiece(dPlateau.getPlateau().getSelectedPiece());
                }
            }
        }
    }

    /**
     * Cette méthode permet de déselectionner la pièce qu'on a déplacé.
     */
    public void mouseReleased(MouseEvent e) {
        if(dPlateau.getPlateau().getSelectedPiece() != null){
            //Si sa dernière position est différente de sa position initial alors on la déselectionne
            //Ca veut dire qu'on à déplacer la pièce
            if(lastPos != initialPos){
                dPlateau.getPlateau().unSelectPiece();
            }
        }
    }

    /**
     * Cette méthode permet de sélectionner une pièce dans le plateau (clic gauche) et d'effectuer une rotation à n'importe quelle pièce (clic droit)
     */
    public void mouseClicked(MouseEvent e) {
        
        //On récupère le bouton de la souris utilisé
        int buttonDown = e.getButton();

        int x = e.getX()/(dPlateau.getWidth()/dPlateau.getPlateau().getTailleX());
        int y = e.getY()/(dPlateau.getHeight()/dPlateau.getPlateau().getTailleY());
        Position posTMP = new Position(x,y);
        //Bouton GAUCHE enfoncé
        if (buttonDown == MouseEvent.BUTTON1) {
            //On récupère la pièce coresspondant à la position du clic
            PiecePuzzle curentPiece = dPlateau.getPlateau().getPieceEnPos(posTMP);
            //On vérifie que l'utilisateur à cliquer sur une pièce et non une case vide
            if(curentPiece != null){
                //Permet de faire fonctionner l'interface avec le terminal
                if(dPlateau.getPlateau().getSelectedPiece() != null){
                    dPlateau.getPlateau().ajouterPiece(dPlateau.getPlateau().getSelectedPiece());
                    dPlateau.getPlateau().unSelectPiece();
                }
                //On récupère la position initial de la pièce
                //Permet de gérer un bug qui permetter à l'utilisateur de supprimer une pièce du plateau
                initialPos = curentPiece.centerPlateau();
                lastPos = initialPos;
                //On sélectionne la pièce dans le plateau
                //Cela permet aussi d'afficher la pièce dans la partie droite de l'interface
                dPlateau.getPlateau().setSelectedPiece(curentPiece);
            }

            // Bouton DROIT enfoncé
        }else if(buttonDown == MouseEvent.BUTTON3) {

            PiecePuzzle curentPiece = dPlateau.getPlateau().getPieceEnPos(posTMP);
            //On vérifie que l'utilisateur à cliquer sur une pièce et non une case vide
            if(curentPiece == null){
                return;
            }else{
                //On essaye de faire une rotaion vers la droite 
                if(!dPlateau.getPlateau().rotationPiece(curentPiece,true)){
                    //Si la rotation vers la droite n'a pas fonctionée alors on essaye une rotaion vers la gauche
                    if(!dPlateau.getPlateau().rotationPiece(curentPiece,false)){
                        //Si aucune des rotations n'a fonctionée on affiche une pop-up contenant un message d'erreur
                        JOptionPane.showMessageDialog(null,"La pièce ne peut pas être tourné sans causer de collisions ici !","Attention",1);
                    }
                }
            }
        }
    }

}
