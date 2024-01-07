package modèle.ChainOfResponsibility;
import modèle.*;

public interface Maillon{
    
    public void actionValide(PiecePuzzle p,PlateauPuzzle plateau);

    public void setSuivant(Maillon m);

    
}