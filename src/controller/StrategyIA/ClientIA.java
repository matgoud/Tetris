package controller.StrategyIA;
import modèle.*;
/**
* Cette classe permet de gérer le lancement de l'IA souhaitée.
*/
public class ClientIA{
    public StrategyIA strategy;
    int nbCoupMax;
    int startDelay;
    int coupDelay;

    public ClientIA(int intelIA, PlateauPuzzle plateau,int nbCoupMax, int startDelay, int coupDelay){
        this.nbCoupMax=nbCoupMax;
        this.startDelay=startDelay;
        this.coupDelay=coupDelay;
        if(intelIA==1) this.strategy=new MoinsBasiqueIA(plateau,nbCoupMax,startDelay,coupDelay);
        else this.strategy=new BasiqueIA(plateau,nbCoupMax,startDelay,coupDelay);
    }

    /**
     * Permet de lancer le fonctionnement de l'IA.
    */
    public void faireJouer(){
        strategy.jouer();
    }

}
