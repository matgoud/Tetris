package modèle;

import java.util.*;
import java.awt.Color;
/**
 * Cette classe permet de manipuler des pièces.
 */
public class PiecePuzzle extends ModeleEcoutable{

    public int couleur;
    public int tailleX;
    public int tailleY;
    private int compteurRotation;
    public Position pos;
    public boolean[][] piece;//attention c'est trompeur dans le double tableau les axes x et y doivent être inversés
    /**
     * @param pos La position de la piece dans le plateau 
     * @param tailleX la taille en X de la piece
     * @param tailleY la taille en Y de la piece
     * @param forme la forme de la piece
     * @param rotation le nombre de rotation de 90 degres appliqué à la piece
     * @param couleur la couleur de la piece
     */
    public PiecePuzzle(Position pos,int tailleX,int tailleY,boolean[][] forme,int rotation,int couleur){
        this.pos                = pos;
        this.compteurRotation   = rotation;
        this.tailleX            = tailleX;
        this.tailleY            = tailleY;
        this.piece              = forme;
        this.couleur            = couleur;



        changement();
        //this.piece              = new boolean[getX()][getY()];

    }

    public int getRotation(){
        return compteurRotation;
    }

    public int getTailleX(){
        return tailleX;
    }

    public int getTailleY(){
        return tailleY;
    }

    public int getRotatedTailleX(){
        if(compteurRotation == 0 || compteurRotation == 2)return tailleX;
        return tailleY;
    }

    public int getRotatedTailleY(){
        if(compteurRotation == 0 || compteurRotation == 2)return tailleY;
        return tailleX;
    }

    public int getCouleur(){
        return couleur;
    }

    public boolean[][] getPiece(){
        return piece;
    }
    /**
     * mais modifie une case dans la pièce.
     * @param X La pos X que l'on cherche.
     * @param Y La pos Y que l'on cherche.
     * @param val La valeur que l'on souhaite mettre à cette Position.
     */
    public void setPieceXY(int X,int Y,boolean val){
        Position p = getRotatedCord(X,Y);
        piece[p.getX()][p.getY()] = val;
    }

    /**
     * @param X Position x de la case que l'on cherche.
     * @param Y Position y de la case que l'on cherche.
     * @return True si une pièce est à une position demandé False sinon.
     */
    public boolean getPieceXY(int X, int Y){
        Position p = getRotatedCord(X,Y);
        if(p.getX() >= 0 && p.getX()<tailleX && p.getY() >= 0 && p.getY()<tailleY){
            if(piece[p.getX()][p.getY()]){
                return true;
            }
        }
        return false;
    }

    public Position centerPiece(){
        int i=getX()/2;
        int j=getY()/2;
        return new Position(i,j);
        
    }

    public Position centerPlateau(){
    
        return pos;
        
    }

    public String toString(){
        String ch="";
        for(int j = 0;j<getY();j++){
            for(int i =0;i<getX();i++){
                Position p = getRotatedCord(i,j);
                //System.out.println("coord:" +p.getX()+" "+p.getY());
                //System.out.println("coordMax:"+getX()+" "+getY());
                if(p.getX()<tailleX && p.getY()<tailleY){
                   if(piece[p.getX()][p.getY()]){
                        ch+="1 ";
                    }
                    else{
                        ch+="0 ";  
                    } 
                }
                
            }
            ch+="\n";
        }
        return ch;
    }
    /**
     * Fait tourner la piece vers la gauche
     */
    public void rotationGauche(){
        compteurRotation = (compteurRotation+1)%4;
        changement();
    }
    /**
     * Fais tourner la piece vers la droite
     */
    public void rotationDroite(){
        compteurRotation =(compteurRotation-1)%4;
        if(compteurRotation<0){
            compteurRotation = compteurRotation +4;
            changement();
        }
        changement();
    }
    /**
     * @param x la position x qui va etre modifiée (ou non).
     * @param y la position y qui va etre modifiée (ou non).
     * @return La position x,y avec la rotation prise en compte.
     */
    public Position getRotatedCord(int x,int y){
        if(compteurRotation ==0){
            return new Position(x,y);
        }

        if(compteurRotation==1){
            return new Position((tailleX-1)-y,x);
        }

        if(compteurRotation==2){
            return new Position((tailleX-1)-x,(tailleY-1)-y);
        }
        if(compteurRotation==3){
            return new Position(y,(tailleY-1)-x);
        }
        return null;
    }
    /**
     * @return Renvoi la taille X de la piece avec la rotation prise en compte.
     */
    public int getX(){
        if(compteurRotation %2 ==1){
            return this.tailleY;
        }
        return this.tailleX;

    }
    /**
     * @return Renvoi la taille Y de la piece avec la rotation prise en compte.
     */
    public int getY(){
        if(compteurRotation %2 ==1){
            return this.tailleX;
        }
        return this.tailleY;

    }
    /**
     * @return Renvoi la Position la plus en haut à gauche possible de la piece.
     */
    public Position getPositionMin(){//avec x et y qui valent le moins possible
        int x = pos.getX()-getX()/2;
        int y = pos.getY()-getY()/2;
        Position res = new Position(x,y);
        return res;
    }
    /**
     * @return Renvoi la Position la plus en bas à droite Possible de la piece.
     */
    public Position getPositionMax(){
        int x = pos.getX()+getX()/2 - (getX()+1)%2;
        int y = pos.getY()+getY()/2 - (getY()+1)%2;
        Position res = new Position(x,y);
        return res;
    }

    public void setPos(Position newPos){
        pos = newPos;
        changement();
    }

    

}