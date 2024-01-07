package controller.StrategyIA;
import modèle.*;

import java.util.Set;
import java.util.HashSet;

/**
* Cette classe abstraite regroupe les fonctions utile à toutes les IA.
*/
public abstract class StrategyIA{
    PlateauPuzzle plateau;
    int nbCoupMax;
    int startDelay;
    int coupDelay;
    int borderHaut;
    int borderDroit;
    int borderBas;
    int borderGauche;

    public StrategyIA(PlateauPuzzle plateau,int nbCoupMax, int startDelay, int coupDelay){
        this.plateau=plateau;
        this.nbCoupMax=nbCoupMax;
        this.startDelay=startDelay;
        this.coupDelay=coupDelay;
        updateBorders();
    }
    /**
     * Change les valeurs des borders; une bordure est une valeur qui indique qu'il n'y a aucune pièce au dela de cette valeur, dans la direction donné.
     * Par exemple, si borderGauche est à 2, il n'y a aucune pièce ayant une case à gauche de la colonne 2, donc en colonne 0 et 1.
     */
    public void updateBorders(){
        borderHaut=plateau.getTailleY();
        borderDroit=0;
        borderBas=0;
        borderGauche=plateau.getTailleX();
        for(PiecePuzzle p : plateau.getPlateau()){
            if(p.getPositionMin().getX()<borderGauche) borderGauche=p.getPositionMin().getX();
            else if(p.getPositionMin().getY()<borderHaut) borderHaut=p.getPositionMin().getY();
            else if(p.getPositionMax().getX()>borderDroit) borderDroit=p.getPositionMax().getX();
            else if(p.getPositionMax().getY()>borderBas) borderBas=p.getPositionMax().getY();
        }
    }
    /**
     * Récupère toutes les pièces qui sont le plus dans la direction donné.
     * Par exemple on va regarder quels pièces ont leur case la plus hautes, le plus haut.
     * @param direction sens que l'on regarde (parmis "haut","droit","bas" et "gauche").
     * @return toutes les pièces dans la direction donné, ou null si la direction n'est pas correct.
     */
    public Set<PiecePuzzle> getPiecesTheMost(String direction){
        if(direction!="haut" && direction!="droit" &&direction!="bas" &&direction!="gauche") return null;
        boolean objMin = false;
        boolean objMax = false;
        boolean onXaxis = false;
        boolean onYaxis = false;
        int repere = 0;
        Set<PiecePuzzle> rtn = new HashSet<>();
        if(direction=="haut" || direction=="gauche"){
            objMin =true;
            repere= Math.max(plateau.getTailleX(),plateau.getTailleY());
        }
        else{
            objMax = true;
        }
        if(direction=="haut" || direction=="bas") onYaxis=true;
        else onXaxis=true;

        for(PiecePuzzle p : plateau.getPlateau()){
            int posVoulu;
            if(objMin && onXaxis) posVoulu= p.getPositionMin().getX();
            else if(objMin && onYaxis) posVoulu= p.getPositionMin().getY();
            else if(objMax && onXaxis) posVoulu= p.getPositionMax().getX();
            else posVoulu= p.getPositionMax().getY();
            if(posVoulu==repere) rtn.add(p);
            else if((objMin && posVoulu<repere) || (objMax && posVoulu>repere)){
                repere = posVoulu;
                rtn = new HashSet<>();
                rtn.add(p);
            }
        }
        return rtn;
    }
    /**
     * Indique le nombre de cases que notre pièce peut se déplacer DEPUIS la direction donné, sans dépasser les borders ;
     * autrement dis, si la direction est haut, on regarde combien de cases peut on aller vers le bas.
     * @param direction sens que l'on regarde (parmis "haut","droit","bas" et "gauche").
     * @param p pièce dont on regarde la marge.
     * @return la marge de la pièce depuis la direction voulu.
     */
    public int getMarge(String direction, PiecePuzzle p){
        if(direction=="haut") return borderBas - p.getPositionMax().getY();
        else if(direction=="droit") return p.getPositionMin().getX() - borderGauche;
        else if(direction=="bas") return p.getPositionMin().getY() - borderHaut;
        else return borderDroit - p.getPositionMax().getX();
    }
    /**
     * Retourne une nouvelle position, décalé d'un nombre de cases donné DEPUIS la direction donné;
     * autrement dis, si la direction est haut, on décale de point vers le bas.
     * @param direction sens que l'on regarde (parmis "haut","droit","bas" et "gauche").
     * @param pos point de base.
     * @param dec nombre de cases de décalage.
     * @return un nouveau point décalé.
     */
    public Position decalePos(String direction, Position pos, int dec){
        if(direction=="haut") return new Position(pos.getX(),pos.getY()+dec);
        else if(direction=="droit") return new Position(pos.getX()-dec,pos.getY());
        else if(direction=="bas") return new Position(pos.getX(),pos.getY()-dec);
        else return new Position(pos.getX()+dec,pos.getY());
    }


    /**
     * Essaie de déplacer la pièce dans la direction donné. 
     * Au possible, la déplace le plus proche du centre que possible (a mi chemin entre les borders, verticales ou horizontales selon la direction).
     * @param direction sens que l'on regarde (parmis "haut","droit","bas" et "gauche").
     * @param p pièce que l'on souhaite déplacer.
     * @return le nombre de case déplacés.
     */
    public int tryMovePieceFrom(String direction,PiecePuzzle p){
        if(direction!="haut" && direction!="droit" &&direction!="bas" &&direction!="gauche") return 0;
        Position center = p.centerPlateau();
        int marge=getMarge(direction,p);
        int caseTriedMoved = 1;
        int caseSuccessfullyMoved=0;
        while(caseTriedMoved+caseSuccessfullyMoved<=marge){
            Position newCentre=decalePos(direction,center,caseTriedMoved);
            p.setPos(newCentre);
            if(plateau.movePiece(p)) caseSuccessfullyMoved=caseTriedMoved;
            caseTriedMoved++;
        }
        Position trueNewCentre=decalePos(direction,center,caseSuccessfullyMoved);
        p.setPos(trueNewCentre);
        plateau.movePiece(p);
        return caseSuccessfullyMoved;
    }

    /**
     * Lance le fonctionnement de l'IA.
    */
    public abstract void jouer();
    

}