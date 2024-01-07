package modèle.ChainOfResponsibility;
import modèle.*;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class MaillonCollision implements Maillon{
    private Maillon suivant;
    private PiecePuzzle pieceTemp;
    
    
    public MaillonCollision(PiecePuzzle pieceTemp){
        this.pieceTemp = pieceTemp;
        

    }
    /**
     * @param m prend un maillon en arg suivant afin de constituer une chaine.
     */
    public void setSuivant(Maillon m){
        this.suivant = m;

    }
    /**
     * @param piece La piece .
     */
    public void actionValide(PiecePuzzle piece,PlateauPuzzle plateau){
        boolean cadena = false;

        Position stock1 = pieceTemp.getPositionMin();//creation de la delimitions des deux pieces
        Position stock11 = pieceTemp.getPositionMax();
        Position stock2 = piece.getPositionMin();
        Position stock22 = piece.getPositionMax();
        

        ArrayList<Position> zoneATester = plateau.getCoordCommun(stock1,stock11,stock2,stock22);
        if(zoneATester!=null){
            Position coordHG = zoneATester.get(0);//point en haut à gauche de la zone à tester
            Position coordBD = zoneATester.get(1);//point en bas à droite de la zone à tester
            
            Position correction1 = piece.centerPlateau();
            Position correction2 = pieceTemp.centerPlateau();
            for(int i = coordHG.getX();i<=coordBD.getX();i++){
                for(int j = coordHG.getY();j<=coordBD.getY();j++){
                    
                    if(piece.getPieceXY(i-(correction1.getX() - piece.getX()/2),j-(correction1.getY() - piece.getY()/2)) && pieceTemp.getPieceXY(i-(correction2.getX() - pieceTemp.getX()/2),j-(correction2.getY() - pieceTemp.getY()/2))){//verification si il y une cases commune entre les 2 piece
                        
                        cadena = true;
                    }
                }
            }
        }
        if(!cadena){
            if(suivant!=null){
                suivant.actionValide(piece,plateau);
            }
            else{
                
                plateau.getPlateau().add(piece);
                
            }
        }
    }
}