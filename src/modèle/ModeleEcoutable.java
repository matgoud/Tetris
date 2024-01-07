package modèle;
import java.util.ArrayList;
import vue.*;
/**
 * Cette classe abstraite représente un modele de jeu.
 */
public abstract class ModeleEcoutable {
    private ArrayList<Ecouteur> objs = new ArrayList<>();
    /**
     * Cette méthode permet d'ajouter un écouteur à la liste des écouteurs.
     * @param obj Ecouteur à ajouter.
     */
    public void ajoutEcouteur(Ecouteur obj){
        objs.add(obj);
    }
    /**
     * Cette méthode permet de supprimer un écouteur de la liste des écouteurs.
     * @param idx Indice de l'écouteur à supprimer.
     */
    public void retraitEcouteur(int idx){
        objs.remove(idx);
    }
    /**
     * Cette méthode permet de mettre à jour la liste des écouteurs.
     */
    public void changement(){
        for (Ecouteur i : objs) {
            i.MiseAJour(this);
        }
    }

}