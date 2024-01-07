package modèle.Strategy;
import modèle.*;
import java.util.Random;
import modèle.Factory.*;
public class GenRandS implements Strategy{

    /**
     * @param plateau  Le plateau qui va se faire remplir.
     * @param numb  Le nombre de piece à integrer.
     * @param difficulte Un entier qui correspond à la taille maximal des pieces à integrer.
     * @return Rempli le plateau avec le nombre de pieces en forme de S indiquée ou moins s'il n'y a plus de place.
     */
    public void initPlateauRand(PlateauPuzzle plateau , int numb , int difficulte){
        int i =0;
        int j =0;
        int antiloop = 0;
        //la valeur 100 est arbitraire elle est suffisament grande pour eviter de sortir du while par erreur
        while(i<numb && antiloop<100){
            j++;
            Random r = new Random();
            PiecePuzzle newPiece = new PieceBuilder().aleatoire(r,
            plateau.getTailleX()-(1+difficulte/2),//zone dans laquel on peut pas placer de piece afin d'eviter des index out of bound
            plateau.getTailleY()-(1+difficulte/2),//idem
            1+(difficulte/2)+difficulte%2,//idem
            1+(difficulte/2)+difficulte%2,//idem
            4+difficulte,//taille de la piece Max (en X)-1
            4+difficulte,//taille de la piece Max (en Y)-1
            2,//taille de la pice en Minimum (en X)
            2)//taille de la piece en Minimun (en Y)
            .avecFormeS()
            .Build();

            if(plateau.ajouterPiece(newPiece)){
                i++;
                antiloop = 0;
            }
            else{
                antiloop++;
            }
        }
        
    }
}