import vue.*;
import modèle.*;
import modèle.Strategy.*;
import controller.*;
import controller.StrategyIA.*;
import java.util.Random;

public class main{


    public static void delai(int milliseconds){
        try {
              Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
              e.printStackTrace();
        }
    }

    public static PlateauPuzzle manageMenu(){

        Menu menu = new Menu();

        while (!menu.getSetDificult()){
            delai(100);
        }

        PlateauPuzzle p = menu.getP();
        menu.getMenu().dispose();
        menu = null;
        return p;

    }

    public static void main(String[] args){
        int niveauIA = 0 ; //S'il est mis à 1, l'ia sera plus performante, mais il y a parfois des 'ConcurrentModificationException'
        PlateauPuzzle plateau = manageMenu();
        ControllerJoueur cj = new ControllerJoueur(plateau,niveauIA);
        Object c = cj.choixIA();
        if(c instanceof ClientIA){
            Interface i = new Interface(plateau);
            VueTerminal t = new VueTerminal(plateau);
            ClientIA ci = (ClientIA)c;
            ci.faireJouer();
        }else{
            Interface i = new Interface(plateau);
            VueTerminal t = new VueTerminal(plateau);
            ControllerInterface ci = new ControllerInterface(i.getDpiece(),i.getDplateau());
            ControllerTerminal ct = new ControllerTerminal(plateau);
        }
    }
}
