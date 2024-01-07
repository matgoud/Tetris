package modèle;
/**
 * Cette class fonctionne sur le meme principe qu'un couple d'entier (x,y)
 */
public class Position{
    int a;
    int b;

    /**
     * @param a la position x
     * @param b la position y
     */
    public Position(int a,int b){
        this.a =a;
        this.b=b;
    }
    /**
     * @return Renvoi la position x.
     */
    public int getX(){
        return a;
    }
    /**
     * @return Renvoi la position y.
     */
    public int getY(){
        return b;
    }
    public String toString(){
        String ch ="";
        ch += "("+getX()+","+getY()+")";
        return ch;
    }
}
