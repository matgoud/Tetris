package controller;

import modèle.*;
import controller.StrategyIA.*;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * Cette Classe permet de choisir qui joue.
 */
public class ControllerJoueur{

    private PlateauPuzzle p;
    private int niveauIA;
    public ControllerJoueur(PlateauPuzzle p,int niveauIA){
        this.p = p;
        this.niveauIA=niveauIA;
    }

    /**
    * Cette méthode permet de choisir si on veut que ce soit une IA qui joue.
    */
    public ClientIA choixIA(){
        JCheckBox IA = new JCheckBox("IA",true);
        int adversaire = JOptionPane.showConfirmDialog(IA,"Voulez vous faire jouer une IA","Choix du joueur",0);
        if(adversaire == 0){
            int nbCoupMax=30;
            int startDelay=3000;
            int coupDelay=500;
            ClientIA c = new ClientIA(niveauIA,p,nbCoupMax,startDelay,coupDelay);
            return c;
        }else{
            return null;
        }
    }

}