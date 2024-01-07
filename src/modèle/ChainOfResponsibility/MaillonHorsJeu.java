package modèle.ChainOfResponsibility;
import modèle.*;

public class MaillonHorsJeu implements Maillon{

    private Maillon suivant;
    private Position centre;
    private int tailleX;
    private int tailleY;
    /**
     * @param centre La position de la piece qui va etre ajouté.
     * @param tailleX La tailleX de la piece.
     * @param tailleY La tailleY de la piece.
     */
    public MaillonHorsJeu(Position centre,int tailleX,int tailleY){
        this.centre = centre;
        this.tailleX = tailleX;
        this.tailleY = tailleY; 

    }
    /**
     * @param m prend un maillon en arg suivant afin de constituer une chaine.
     */
    public void setSuivant(Maillon m){
        this.suivant = m;

    }
    /**
     * @param piece La piece qui doit etre ajouté.
     * @param plateau Le plateau où la piece doit etre ajouté.
     * @return Si la piece n'est pas situé en dehors du plateau alors elle instancie son maillon suivant autrement elle s'arrete.
     */
    public void actionValide(PiecePuzzle piece,PlateauPuzzle plateau){
        if((centre.getX()) + (piece.getX()/2)>tailleX || (centre.getY())+(piece.getY()/2)>tailleY || (centre.getX()) - (piece.getX()/2)<0 || (centre.getY())+(piece.getY()/2)<0){
            
        }

        else{
            if(suivant!=null){
               suivant.actionValide(piece,plateau); 
            }
            else{
                
                plateau.getPlateau().add(piece);
                
            }
            
            


        }

    }
    
}