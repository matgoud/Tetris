package controller.StrategyIA;
import modèle.*;
import java.util.Set;
import java.util.HashSet;
import java.awt.event.*;
import javax.swing.*;

/**
 * Cette IA va jouer en déplacant les pièces dans une direction à la fois, tout en sachant tourner la pièce et la déplacer latéralement si besoin est. 
 */
public class MoinsBasiqueIA extends StrategyIA{

    public MoinsBasiqueIA(PlateauPuzzle plateau,int nbCoupMax, int startDelay, int coupDelay){
        super(plateau,nbCoupMax,startDelay,coupDelay);
    }
    /**
     * L'IA va essayer de réaliser des déplacements jusqu'à atteindre une condition de fin.
     * Elle va alterner d'essayer de déplacer les pièces les plus en haut, à droite, en bas, et à gauche, c'est à dire leur bord, et essayer de les éloigner de leur bord respectif.
     * Les déplacement ont pour but d'aller dans la direction opposé au bord dont provient la pièce, mais peuvent inclure des déplacement latéraux à celui ci, et des rotations de la pièce.
     * Dès qu'un déplacement valable qui éloigne la pièce du bord est trouvé, on passe à la prochaine pièce.
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
                    Position centreDepart = p.centerPiece();
                    Position pointDepart = p.centerPlateau();
                    int decViaRota=0;
                    boolean moveDone=false;
                    String dirAlt1="";
                    String dirAlt2="";
                    for(int i=0;i<directions.length;i++){
                        if(directions[i]==d){
                            dirAlt1=directions[(i+1)%directions.length];
                            dirAlt2=directions[(i+3)%directions.length];
                        }
                    }

                    for(int nbRota=0;nbRota<4;nbRota++){
                        if(!moveDone && nbRota!=0){
                            p.rotationDroite();
                            if(nbRota==2) decViaRota=0;
                            else{
                                decViaRota=centreDepart.getY()-centreDepart.getX();
                                if(d=="haut" || d=="bas") decViaRota=-decViaRota;
                                if(d=="droit") decViaRota+= p.getX()%2 - p.getY()%2;
                                if(d=="bas") decViaRota+= p.getY()%2 - p.getX()%2;
                            }
                        }
                        int marge = getMarge(d,p);
                        int margeAlt1 = getMarge(dirAlt1,p);
                        int margeAlt2 = getMarge(dirAlt2,p);
                        for(int dec=0;dec<Math.max(margeAlt1,margeAlt2);dec++){
                            if(!moveDone && dec==0){
                                tenta++;
                                if(decViaRota<0){
                                    decViaRota=0;
                                    if(plateau.movePiece(p)){
                                        moveDone=true;
                                    }
                                }
                                else if(tryMovePieceFrom(d,p)>decViaRota){
                                    moveDone=true;
                                }
                            }
                            else{
                                if(!moveDone && margeAlt1>dec){
                                    Position posDecale1 = decalePos(dirAlt1,pointDepart,dec);
                                    p.setPos(posDecale1);
                                    tenta++;
                                    if(tryMovePieceFrom(d,p)>decViaRota){
                                        moveDone=true;
                                    }
                                }
                                if(!moveDone && margeAlt2>dec){
                                    Position posDecale2 = decalePos(dirAlt2,pointDepart,dec);
                                    p.setPos(posDecale2);
                                    tenta++;
                                    if(tryMovePieceFrom(d,p)>decViaRota){
                                        moveDone=true;
                                    }
                                }
                                if(!moveDone) p.setPos(pointDepart);
                            }
                        }
                    }
                    if(moveDone){
                        try {
                            Thread.sleep(coupDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        coupPlayed++;
                        movePlayed=true;
                    }
                    else{
                        p.rotationDroite();
                        p.setPos(pointDepart);
                        plateau.movePiece(p);
                        try {
                            Thread.sleep(coupDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
    
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
