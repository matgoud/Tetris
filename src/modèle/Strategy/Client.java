package modèle.Strategy;
import modèle.*;
/**
 * Cette classe s'occupe de gérer les differents appels au classe implémentant Strategy.
 */
public class Client{
    public Strategy strategy;


    /**
     * Creer une initialisation de plateau avec strategy ayant été defini plus tot.
     */
    public void creerInitPlateau(PlateauPuzzle plateau,int nbr,int difficulte){
        strategy.initPlateauRand(plateau,nbr,difficulte);
    }
    /**
     * Permet de decider quel Strategy va etre effectué.
     */
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }



}
