package controller.StrategyIA;
import modèle.*;
import java.util.Set;
import java.util.HashSet;
import java.awt.event.*;
import javax.swing.*;

/**
 * Cette IA va jouer en ne déplacant les pièces que dans une seule direction à la fois, et sans les tourner.
 */
public class BasiqueIA extends StrategyIA{

    public BasiqueIA(PlateauPuzzle plateau,int nbCoupMax, int startDelay, int coupDelay){
        super(plateau,nbCoupMax,startDelay,coupDelay);
    }
    /**
     * L'IA va essayer de réaliser des déplacements jusqu'à atteindre une condition de fin.
     * Elle va alterner d'essayer de déplacer les pièces les plus en haut, à droite, en bas, et à gauche, c'est à dire leur bord, et essayer de les éloigner de leur bord respectif.
     * Les déplacement ne se font que dans la direction opposé au bord dont provient la pièce.
     * Les conditions d'arrets sont celles ci ; une seule suffit pour arreter la fonction :
     * - Le nombre de coup maximum a été réalisé (s'il c'est un nombre négatif, alors il n'y a pas de maximum).
     * - Un cycle haut/droit/bas/gauche a eu lieu sans déplacer une seule pièce.
     * - 3 cycles haut/droit/bas/gauche ont eu lieu sans améliorer le score.
     */
    public void jouer(){
        try {
            Thread.sleep(startDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] directions = {"haut","droit","bas","gauche"};
        boolean movePlayed = true;
        int coupPlayed = 0;
        int threeFoldRepetition=0;
        int tenta=0;
        int score=plateau.resultat();

        while(movePlayed && threeFoldRepetition<3 && (nbCoupMax<0 || nbCoupMax>coupPlayed)){
            movePlayed=false;
            for(String d : directions){
                for(PiecePuzzle p : getPiecesTheMost(d)){
                    if(nbCoupMax<0 || nbCoupMax>coupPlayed){
                        if(tryMovePieceFrom(d,p)>0){
                            try {
                                Thread.sleep(2*coupDelay);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            coupPlayed++;
                            movePlayed=true;
                        }
                        tenta++;
                    }
                }
                updateBorders();
            }
            int newScore = plateau.resultat();
            if(newScore<score) score = newScore;
            else threeFoldRepetition++;
        }
        JOptionPane.showMessageDialog(null,"Partie terminée, le score  de l'IA est : "+plateau.resultat(),"Score",1);
        plateau.endGame();
    }
    

}